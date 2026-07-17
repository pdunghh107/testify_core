package com.zcomini.backend.shared.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcomini.backend.shared.api.ApiError;
import com.zcomini.backend.shared.api.ErrorMessageSanitizer;
import com.zcomini.backend.shared.tenant.RequestContext;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

final class ApiErrorResponseWriter {

    private ApiErrorResponseWriter() {
    }

    static void write(ObjectMapper objectMapper,
                      String serviceName,
                      HttpServletResponse response,
                      HttpStatus status,
                      String code,
                      String message,
                      String path,
                      List<String> details) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");

        ApiError body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                code,
                ErrorMessageSanitizer.sanitizeClientMessage(message, defaultMessage(status)),
                path,
                RequestContext.getCorrelationId(),
                serviceName,
                details == null ? List.of() : List.copyOf(details)
        );

        objectMapper.writeValue(response.getOutputStream(), body);
    }

    private static String defaultMessage(HttpStatus status) {
        return switch (status) {
            case BAD_REQUEST -> "Request is invalid.";
            case UNAUTHORIZED -> "Authentication is required.";
            case FORBIDDEN -> "You do not have permission to perform this action.";
            case NOT_FOUND -> "Requested resource was not found.";
            case CONFLICT -> "Request conflicts with existing data.";
            case UNPROCESSABLE_ENTITY -> "Request cannot be processed.";
            default -> "An unexpected server error occurred.";
        };
    }
}
