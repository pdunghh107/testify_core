package com.zcomini.backend.testify.service;

import com.zcomini.backend.testify.dto.LogMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncLogWriterService {

    private final BlockingQueue<LogMessage> logQueue;

    @Value("${testify.log-dir:D:/test_results}")
    private String logDir;

    @PostConstruct
    public void initBackgroundWorker() {
        try {
            Files.createDirectories(Paths.get(logDir));
        } catch (IOException e) {
            log.error("Không thể tạo thư mục lưu log: {}", logDir, e);
        }

        Thread worker = new Thread(() -> {
            log.info("AsyncLogWriterService Worker đã khởi chạy, lắng nghe tại hàng đợi...");
            while (true) {
                try {
                    // take() blocks luồng này nếu Queue rỗng
                    LogMessage msg = logQueue.take();
                    writeToFile(msg);
                } catch (InterruptedException e) {
                    log.error("Worker ghi log bị ngắt!", e);
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        
        worker.setName("Async-Log-Writer-Thread");
        worker.setDaemon(true); // JVM có thể tắt khi tắt app
        worker.start();
    }

    private void writeToFile(LogMessage msg) {
        String fileName = String.format("%s/Case_%03d.md", logDir, msg.getCaseId());
        try (FileWriter writer = new FileWriter(new File(fileName))) {
            writer.write("# Kịch bản [" + msg.getCaseId() + "]: " + msg.getDescription() + "\n");
            writer.write("## 📊 KẾT QUẢ ĐÁNH GIÁ: " + msg.getTestResult() + "\n");
            writer.write("- Mã lỗi thực tế: " + msg.getActualStatus() + "\n\n");
            writer.write("### 📤 Dữ liệu gửi đi (Payload):\n```json\n" + msg.getPayload() + "\n```\n\n");
            writer.write("### 📥 Nội dung phản hồi (Response Body):\n```json\n" + msg.getResponseBody() + "\n```\n");
        } catch (Exception ex) {
            log.error("Lỗi khi ghi file cho Case {}", msg.getCaseId(), ex);
        }
    }
}
