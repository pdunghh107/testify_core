package com.zcomini.backend.shared.api;

import java.time.Instant;
import java.util.List;

/**
 * Cấu trúc response lỗi chuẩn cho toàn bộ FizaHub Microservices.
 *
 * <pre>{@code
 * {
 *   "timestamp": "2026-04-14T08:30:00Z",
 *   "status": 404,
 *   "error": "Not Found",
 *   "code": "ORDER.NOT_FOUND",
 *   "message": "Requested resource was not found.",
 *   "path": "/api/v1/orders/abc",
 *   "traceId": "3fa2b1c8-...",
 *   "service": "order-service",
 *   "details": []
 * }
 * }</pre>
 */
public record ApiError(
        /** Thời điểm xảy ra lỗi (UTC ISO-8601). */
        Instant timestamp,
        /** HTTP status code (VD: 400, 404, 500). */
        int status,
        /** HTTP status phrase (VD: "Not Found", "Bad Request"). */
        String error,
        /** Mã lỗi nghiệp vụ theo namespace (VD: "ORDER.NOT_FOUND"). Xem {@link ApiErrorCode}. */
        String code,
        /** Thông điệp mô tả lỗi, an toàn để hiển thị cho end-user. */
        String message,
        /** Đường dẫn API gây ra lỗi. */
        String path,
        /** Correlation/Trace ID – dùng để tra cứu log trên hệ thống. */
        String traceId,
        /** Tên service phát sinh lỗi (từ spring.application.name). */
        String service,
        /** Danh sách chi tiết lỗi, thường dùng cho validation failures. */
        List<Object> details
) {
}

