package com.zcomini.backend.testify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseWrapper {
    private String description;
    private Map<String, Object> payload;
    private int expectedStatusCode;
}
