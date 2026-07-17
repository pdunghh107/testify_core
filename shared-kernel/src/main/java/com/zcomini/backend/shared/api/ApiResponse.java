package com.zcomini.backend.shared.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zcomini.backend.shared.tenant.RequestContext;

/**
 * Envelope chuẩn cho mọi API success response của FizaHub Microservices.
 * <p>
 * Áp dụng theo chiến lược Option C: wrap dần dần, ưu tiên endpoints mới.
 * Các endpoint cũ sẽ được migrate từng bước mà không ảnh hưởng uptime.
 *
 * <h3>Ví dụ response thành công (single object):</h3>
 * <pre>{@code
 * {
 *   "success": true,
 *   "code": "OK",
 *   "data": { "id": "...", "name": "Fiza Demo" },
 *   "message": null,
 *   "requestId": "3fa2b1c8-0001-4abc-9def-000000000001"
 * }
 * }</pre>
 *
 * <h3>Ví dụ response tạo mới thành công:</h3>
 * <pre>{@code
 * {
 *   "success": true,
 *   "code": "CREATED",
 *   "data": { "id": "..." },
 *   "message": "Resource created successfully.",
 *   "requestId": "3fa2b1c8-..."
 * }
 * }</pre>
 *
 * <h3>Ví dụ response không có data (action/command):</h3>
 * <pre>{@code
 * {
 *   "success": true,
 *   "code": "OK",
 *   "data": null,
 *   "message": "Logged out successfully.",
 *   "requestId": "3fa2b1c8-..."
 * }
 * }</pre>
 *
 * @param <T> Kiểu dữ liệu payload (dùng {@code Void} nếu không có data).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        /** Luôn là {@code true} cho success response. */
        boolean success,
        /** Mã kết quả: "OK" cho thành công, "CREATED" cho tạo mới. */
        String code,
        /** Payload dữ liệu. Có thể {@code null} với action-only responses. */
        T data,
        /** Thông điệp bổ sung (tuỳ chọn). Thường dùng cho action responses. */
        String message,
        /** Correlation ID để trace request trong log hệ thống. */
        String requestId
) {

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    /**
     * Tạo response thành công với dữ liệu, không có message.
     *
     * @param data Dữ liệu cần trả về.
     */
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "OK", data, null, RequestContext.getCorrelationId());
    }

    /**
     * Tạo response thành công với dữ liệu và message mô tả.
     *
     * @param data    Dữ liệu cần trả về.
     * @param message Thông điệp bổ sung.
     */
    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(true, "OK", data, message, RequestContext.getCorrelationId());
    }

    /**
     * Tạo response cho thao tác tạo mới resource (HTTP 201).
     *
     * @param data Dữ liệu resource vừa tạo.
     */
    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(true, "CREATED", data, null, RequestContext.getCorrelationId());
    }

    /**
     * Tạo response cho action/command không trả về data (VD: logout, delete).
     *
     * @param message Thông điệp xác nhận thao tác.
     */
    public static ApiResponse<Void> message(String message) {
        return new ApiResponse<>(true, "OK", null, message, RequestContext.getCorrelationId());
    }

    /**
     * Tạo response thành công với dữ liệu phân trang.
     * Dùng kết hợp với {@link PageResponse}.
     *
     * @param page {@link PageResponse} chứa danh sách và metadata pagination.
     */
    public static <T> ApiResponse<PageResponse<T>> paged(PageResponse<T> page) {
        return new ApiResponse<>(true, "OK", page, null, RequestContext.getCorrelationId());
    }
    /**
     * Tạo response thành công chỉ với mã ApiSuccessCode (dùng cho các action không trả về data).
     *
     * @param code Mã sự kiện thành công theo chuẩn ApiSuccessCode
     */
    public static ApiResponse<Void> ok(ApiSuccessCode code) {
        return new ApiResponse<>(true, code.value(), null, null, RequestContext.getCorrelationId());
    }

    /**
     * Tạo response thành công với mã ApiSuccessCode và dữ liệu.
     *
     * @param code Mã sự kiện thành công theo chuẩn ApiSuccessCode
     * @param data Dữ liệu cần trả về.
     */
    public static <T> ApiResponse<T> ok(ApiSuccessCode code, T data) {
        return new ApiResponse<>(true, code.value(), data, null, RequestContext.getCorrelationId());
    }

    /**
     * Tạo response thành công với mã ApiSuccessCode, dữ liệu và message mô tả.
     *
     * @param code    Mã sự kiện thành công theo chuẩn ApiSuccessCode
     * @param data    Dữ liệu cần trả về.
     * @param message Thông điệp bổ sung.
     */
    public static <T> ApiResponse<T> ok(ApiSuccessCode code, T data, String message) {
        return new ApiResponse<>(true, code.value(), data, message, RequestContext.getCorrelationId());
    }

    /**
     * Tạo response cho thao tác tạo mới resource (HTTP 201) với mã ApiSuccessCode.
     *
     * @param code Mã sự kiện thành công theo chuẩn ApiSuccessCode
     * @param data Dữ liệu resource vừa tạo.
     */
    public static <T> ApiResponse<T> created(ApiSuccessCode code, T data) {
        return new ApiResponse<>(true, code.value(), data, null, RequestContext.getCorrelationId());
    }

    /**
     * Tạo response cho action/command không trả về data (VD: logout, delete) với mã ApiSuccessCode.
     *
     * @param code Mã sự kiện thành công theo chuẩn ApiSuccessCode
     * @param message Thông điệp xác nhận thao tác.
     */
    public static ApiResponse<Void> message(ApiSuccessCode code, String message) {
        return new ApiResponse<>(true, code.value(), null, message, RequestContext.getCorrelationId());
    }

    /**
     * Tạo response thành công với dữ liệu phân trang và mã ApiSuccessCode.
     *
     * @param code Mã sự kiện thành công theo chuẩn ApiSuccessCode
     * @param page {@link PageResponse} chứa danh sách và metadata pagination.
     */
    public static <T> ApiResponse<PageResponse<T>> paged(ApiSuccessCode code, PageResponse<T> page) {
        return new ApiResponse<>(true, code.value(), page, null, RequestContext.getCorrelationId());
    }
}
