package com.zcomini.backend.shared.api;

public record ApiErrorDetail(
        String code,
        String field,
        String message
) {}
