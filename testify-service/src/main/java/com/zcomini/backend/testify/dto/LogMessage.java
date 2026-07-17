package com.zcomini.backend.testify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogMessage {
    private int caseId;
    private String description;
    private String payload;
    private String testResult;
    private String actualStatus;
    private String responseBody;
}
