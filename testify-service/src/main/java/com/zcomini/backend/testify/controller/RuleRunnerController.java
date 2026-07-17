package com.zcomini.backend.testify.controller;

import com.zcomini.backend.testify.service.RuleRunnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/v1/rules-runner")
@RequiredArgsConstructor
public class RuleRunnerController {

    private final RuleRunnerService ruleRunnerService;
    private final ExecutorService sseExecutor = Executors.newCachedThreadPool();

    @PostMapping("/run-stream")
    public SseEmitter runTestsStream(@RequestBody Map<String, Object> requestInput) {
        // SSE timeout 5 phút (300_000ms)
        SseEmitter emitter = new SseEmitter(300_000L);

        // Chạy ngầm tránh block HTTP thread chính
        sseExecutor.execute(() -> {
            try {
                ruleRunnerService.executeTestMatrix(requestInput, emitter);
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
