package com.zcomini.backend.shared.api;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;

public final class ErrorMessageSanitizer {

    private static final int MAX_CLIENT_MESSAGE_LENGTH = 240;

    private static final List<String> SENSITIVE_MARKERS = List.of(
            "select ",
            "insert ",
            "update ",
            "delete ",
            " from ",
            " where ",
            " join ",
            "sqlstate",
            "sql [",
            "jdbc",
            "hibernate",
            "psqlexception",
            "postgresql",
            "preparedstatement",
            "constraint [",
            "duplicate key value",
            "violates unique constraint",
            "could not execute statement",
            "syntax error at or near",
            "org.springframework.dao",
            "org.hibernate",
            "jakarta.persistence",
            "java.sql"
    );

    private ErrorMessageSanitizer() {
    }

    public static String sanitizeClientMessage(String message, String fallback) {
        if (!StringUtils.hasText(message)) {
            return fallback;
        }
        String trimmed = message.trim();
        String normalized = trimmed.toLowerCase(Locale.ROOT);
        if (trimmed.length() > MAX_CLIENT_MESSAGE_LENGTH || containsSensitiveMarker(normalized)) {
            return fallback;
        }
        return trimmed;
    }

    public static boolean isDatabaseException(Throwable throwable) {
        Throwable current = throwable;
        while (current != null) {
            String className = current.getClass().getName().toLowerCase(Locale.ROOT);
            if (className.contains("sql")
                    || className.contains("jdbc")
                    || className.contains("hibernate")
                    || className.contains("persistence")
                    || className.contains("dataaccess")
                    || className.contains("datasource")) {
                return true;
            }
            if (containsSensitiveMarker(String.valueOf(current.getMessage()).toLowerCase(Locale.ROOT))) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }

    private static boolean containsSensitiveMarker(String value) {
        for (String marker : SENSITIVE_MARKERS) {
            if (value.contains(marker)) {
                return true;
            }
        }
        return false;
    }
}
