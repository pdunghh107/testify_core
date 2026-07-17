package com.zcomini.backend.testify.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcomini.backend.testify.dto.LogMessage;
import com.zcomini.backend.testify.dto.TestCaseWrapper;
import com.zcomini.backend.testify.dto.TestLogDTO;
import com.zcomini.backend.testify.service.engine.DataGeneratorEngine;
import com.zcomini.backend.testify.service.engine.ValidationMatrixEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermutationTestService {

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final BlockingQueue<LogMessage> logQueue;
    private final DataGeneratorEngine dataGeneratorEngine;
    private final ValidationMatrixEngine validationMatrixEngine;

    @SuppressWarnings("unchecked")
    public void executeTestMatrix(Map<String, Object> requestInput, SseEmitter emitter) {
        String targetUrl = (String) requestInput.get("url");
        String method = (String) requestInput.getOrDefault("method", "POST");
        Map<String, Object> basePayload = (Map<String, Object>) requestInput.get("payload");
        List<String> requiredFields = (List<String>) requestInput.get("required_fields");
        Map<String, Object> constraints = (Map<String, Object>) requestInput.get("constraints");
        Map<String, Object> generators = (Map<String, Object>) requestInput.get("generators");

        // 1. Áp dụng Generator để lấy Fresh Payload
        Map<String, Object> generatedPayload = dataGeneratorEngine.applyGenerators(basePayload, generators);

        // 2. Sinh danh sách kịch bản kiểm thử (Matrix)
        List<TestCaseWrapper> testSuite = validationMatrixEngine.buildTestSuite(generatedPayload, requiredFields, constraints);

        // 3. Thực thi đa luồng bằng Virtual Threads
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < testSuite.size(); i++) {
                final int currentCaseId = i + 1; // 1-indexed for logs
                final TestCaseWrapper testCase = testSuite.get(i);

                executor.submit(() -> {
                    runAndAssertTestCase(targetUrl, method, testCase, currentCaseId, emitter);
                });
            }
        }
        
        // Hoàn tất luồng
        try {
            emitter.send(SseEmitter.event().name("COMPLETED").data("All tests submitted"));
            emitter.complete();
        } catch (Exception e) {
            log.error("Lỗi khi gửi sự kiện COMPLETED", e);
        }
    }

    private void runAndAssertTestCase(String url, String method, TestCaseWrapper testCase, int caseId, SseEmitter emitter) {
        String jsonPayload = "";
        int actualStatus = 500;
        String testResult = "FAILED ❌";
        String responseBodyStr = "";

        try {
            jsonPayload = objectMapper.writeValueAsString(testCase.getPayload());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .method(method, HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // Gọi API, luồng ảo block tại đây nhưng CPU vật lý không block
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            actualStatus = response.statusCode();
            responseBodyStr = response.body();

            // ASSERTION
            boolean isPassed = (actualStatus == testCase.getExpectedStatusCode());
            testResult = isPassed ? "PASSED ✅" : "FAILED ❌";

        } catch (Exception e) {
            log.error("Network / Parse Error for Case {}", caseId, e);
            testResult = "SYSTEM_ERROR 🚨";
            responseBodyStr = e.getMessage();
        } finally {
            // 1. Push log cho Async File Writer (Không block luồng chạy test)
            LogMessage logMessage = new LogMessage(
                    caseId, testCase.getDescription(), jsonPayload, testResult,
                    "HTTP " + actualStatus + " (Kỳ vọng: " + testCase.getExpectedStatusCode() + ")",
                    responseBodyStr
            );
            logQueue.offer(logMessage);

            // 2. Push event về cho Frontend qua SSE
            try {
                TestLogDTO dto = new TestLogDTO(
                        caseId, url, testCase.getExpectedStatusCode(), actualStatus,
                        "QUEUED", testResult + " - " + testCase.getDescription()
                );
                emitter.send(SseEmitter.event().name("LOG_EVENT").data(dto));
            } catch (Exception ex) {
                // Ignore SSE errors if client disconnected
            }
        }
    }
}
