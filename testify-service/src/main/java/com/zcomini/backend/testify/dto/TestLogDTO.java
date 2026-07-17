package com.zcomini.backend.testify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestLogDTO {
    private int caseId;
    private String targetUrl;
    private int expectedStatus;
    private int actualStatus;
    private String fileWriteStatus;
    private String message;
}
