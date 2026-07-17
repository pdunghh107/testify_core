package com.zcomini.backend.shared.web;

import com.zcomini.backend.shared.api.ApiError;
import com.zcomini.backend.shared.api.ApiErrorCode;
import com.zcomini.backend.shared.api.ErrorMessageSanitizer;
import com.zcomini.backend.shared.exception.BusinessException;
import com.zcomini.backend.shared.tenant.RequestContext;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        /**
         * Tên service hiện tại – populate từ spring.application.name, dùng trong
         * ApiError.service
         */
        private final String serviceName;

        public GlobalExceptionHandler(@Value("${spring.application.name:unknown-service}") String serviceName) {
                this.serviceName = serviceName;
        }

        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ApiError> handleBusiness(BusinessException ex, HttpServletRequest request) {
                logBusinessException(ex, request);
                return build(
                                ex.getStatus(),
                                ex.getCode(),
                                ErrorMessageSanitizer.sanitizeClientMessage(ex.getMessage(),
                                                defaultMessage(ex.getStatus())),
                                request,
                                sanitizeDetails(ex.getDetails()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex,
                        HttpServletRequest request) {
                List<com.zcomini.backend.shared.api.ApiErrorDetail> details = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> new com.zcomini.backend.shared.api.ApiErrorDetail(
                                                "COMMON.FIELD_INVALID",
                                                error.getField(),
                                                error.getDefaultMessage()))
                                .toList();
                return build(HttpStatus.BAD_REQUEST, ApiErrorCode.VALIDATION_FAILED.value(), "Dữ liệu chưa hợp lệ.",
                                request, details);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ApiError> handleConstraint(ConstraintViolationException ex, HttpServletRequest request) {
                List<com.zcomini.backend.shared.api.ApiErrorDetail> details = ex.getConstraintViolations()
                                .stream()
                                .map(violation -> new com.zcomini.backend.shared.api.ApiErrorDetail(
                                                "COMMON.FIELD_INVALID",
                                                violation.getPropertyPath().toString(),
                                                violation.getMessage()))
                                .toList();
                return build(HttpStatus.BAD_REQUEST, ApiErrorCode.VALIDATION_FAILED.value(), "Dữ liệu chưa hợp lệ.",
                                request, details);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
                return build(
                                HttpStatus.BAD_REQUEST,
                                ApiErrorCode.BAD_REQUEST.value(),
                                ErrorMessageSanitizer.sanitizeClientMessage(ex.getMessage(), "Yêu cầu chưa hợp lệ."),
                                request,
                                List.of());
        }

        // Lỗi 500 không trả về Santinize
        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
                return build(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ApiErrorCode.INTERNAL_SERVER_ERROR.value(),
                                "Hệ thống đang gặp sự cố nội bộ. Vui lòng thử lại sau.",
                                request,
                                List.of());
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex,
                        HttpServletRequest request) {
                log.warn("Data integrity violation on {} [{}]", request.getRequestURI(),
                                RequestContext.getCorrelationId(), ex);
                return build(
                                HttpStatus.CONFLICT,
                                ApiErrorCode.DATABASE_CONSTRAINT_VIOLATION.value(),
                                "Dữ liệu đang bị trùng hoặc xung đột với thông tin hiện có.",
                                request,
                                List.of(ex.getMostSpecificCause().getMessage()));
        }

        @ExceptionHandler({
                        DataAccessException.class,
                        JpaSystemException.class,
                        TransactionSystemException.class,
                        PersistenceException.class
        })
        public ResponseEntity<ApiError> handleDatabase(Exception ex, HttpServletRequest request) {
                log.error("Database exception [{}] on {} [{}]", ex.getClass().getName(), request.getRequestURI(),
                                RequestContext.getCorrelationId(), ex);
                return build(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ApiErrorCode.DATABASE_ERROR.value(),
                                "Hệ thống dữ liệu đang bận. Vui lòng thử lại sau.",
                                request,
                                List.of(ex.getMessage() != null ? ex.getMessage() : ex.getClass().getName()));
        }

        @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
        public ResponseEntity<ApiError> handleResponseStatus(org.springframework.web.server.ResponseStatusException ex,
                        HttpServletRequest request) {
                HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
                return build(
                                status,
                                ApiErrorCode.defaultForStatus(status),
                                ErrorMessageSanitizer.sanitizeClientMessage(
                                                ex.getReason() != null ? ex.getReason() : status.getReasonPhrase(),
                                                defaultMessage(status)),
                                request,
                                List.of());
        }

        @ExceptionHandler({ NoHandlerFoundException.class, NoResourceFoundException.class })
        public ResponseEntity<ApiError> handleNotFound(Exception ex, HttpServletRequest request) {
                log.warn("Not found [{}] on {} [{}]", ex.getClass().getSimpleName(), request.getRequestURI(),
                                RequestContext.getCorrelationId());
                return build(
                                HttpStatus.NOT_FOUND,
                                ApiErrorCode.NOT_FOUND.value(),
                                defaultMessage(HttpStatus.NOT_FOUND),
                                request,
                                List.of());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiError> handleFallback(Exception ex, HttpServletRequest request) {
                log.error("Unhandled exception [{}] on {}: {}", ex.getClass().getName(), request.getRequestURI(),
                                ex.getMessage(), ex);
                if (ErrorMessageSanitizer.isDatabaseException(ex)) {
                        return build(
                                        HttpStatus.INTERNAL_SERVER_ERROR,
                                        ApiErrorCode.DATABASE_ERROR.value(),
                                        "Hệ thống dữ liệu đang bận. Vui lòng thử lại sau.",
                                        request,
                                        List.of("Fallback DB Error: " + (ex.getMessage() != null ? ex.getMessage()
                                                        : ex.getClass().getName())));
                }
                return build(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ApiErrorCode.INTERNAL_ERROR.value(),
                                "Có lỗi hệ thống. Vui lòng thử lại sau.",
                                request,
                                List.of("Fallback Internal Error: " + (ex.getMessage() != null ? ex.getMessage()
                                                : ex.getClass().getName())));
        }

        // -------------------------------------------------------------------------
        // Internal helpers
        // -------------------------------------------------------------------------

        private ResponseEntity<ApiError> build(HttpStatus status,
                        String code,
                        String message,
                        HttpServletRequest request,
                        List<?> details) {
                ApiError body = new ApiError(
                                Instant.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                code,
                                message,
                                request.getRequestURI(),
                                RequestContext.getCorrelationId(),
                                serviceName,
                                details == null ? List.of() : new java.util.ArrayList<>(details));
                return ResponseEntity.status(status).body(body);
        }

        private void logBusinessException(BusinessException ex, HttpServletRequest request) {
                if (ex.getStatus().is5xxServerError()) {
                        log.error("Business exception [{}] on {} [{}]: {}", ex.getCode(), request.getRequestURI(),
                                        RequestContext.getCorrelationId(), ex.getMessage(), ex);
                        return;
                }
                log.warn("Business exception [{}] on {} [{}]: {}", ex.getCode(), request.getRequestURI(),
                                RequestContext.getCorrelationId(), ex.getMessage());
        }

        private List<Object> sanitizeDetails(List<?> details) {
                return details == null
                                ? List.of()
                                : details.stream()
                                                .map(detail -> {
                                                        if (detail instanceof String s) {
                                                                return ErrorMessageSanitizer.sanitizeClientMessage(s,
                                                                                "Dữ liệu yêu cầu không hợp lệ.");
                                                        } else if (detail instanceof com.zcomini.backend.shared.api.ApiErrorDetail d) {
                                                                return new com.zcomini.backend.shared.api.ApiErrorDetail(
                                                                                d.code(),
                                                                                d.field(),
                                                                                ErrorMessageSanitizer
                                                                                                .sanitizeClientMessage(d
                                                                                                                .message(),
                                                                                                                "Dữ liệu yêu cầu không hợp lệ."));
                                                        }
                                                        return detail;
                                                })
                                                .toList();
        }

        private String defaultMessage(HttpStatus status) {
                return switch (status) {
                        case BAD_REQUEST -> "Yêu cầu chưa hợp lệ.";
                        case UNAUTHORIZED -> "Cần xác thực để tiếp tục.";
                        case FORBIDDEN -> "Bạn không có quyền thực hiện thao tác này.";
                        case NOT_FOUND -> "Không tìm thấy dữ liệu yêu cầu.";
                        case CONFLICT -> "Dữ liệu đang bị trùng hoặc xung đột với thông tin hiện có.";
                        case UNPROCESSABLE_ENTITY -> "Không thể xử lý yêu cầu hiện tại.";
                        case BAD_GATEWAY -> "Dịch vụ phụ trợ đang tạm thời không khả dụng.";
                        case SERVICE_UNAVAILABLE -> "Dịch vụ đang tạm thời không khả dụng.";
                        default -> "Có lỗi hệ thống. Vui lòng thử lại sau.";
                };
        }
}
