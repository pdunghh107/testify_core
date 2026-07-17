package com.zcomini.backend.shared.api;

import org.springframework.http.HttpStatus;

/**
 * Bảng mã lỗi tập trung cho toàn bộ FizaHub Microservices.
 * <p>
 * Naming convention: {@code {NAMESPACE}.{CATEGORY}_{DETAIL}}
 * <p>
 * Các namespace hiện có:
 * <ul>
 * <li>{@code COMMON} – Lỗi chung, không thuộc service cụ thể</li>
 * <li>{@code AUTH} – Authentication & Authorization (auth-service)</li>
 * <li>{@code ACCESS} – Kiểm soát truy cập, tenant/module isolation</li>
 * <li>{@code TENANT} – Quản lý tenant (tenant-service)</li>
 * <li>{@code USER} – Tài khoản nhân viên/người dùng (auth-service)</li>
 * <li>{@code CUSTOMER}– Hồ sơ khách hàng (auth-service / CRM)</li>
 * <li>{@code PRODUCT} – Sản phẩm, danh mục, thương hiệu (product-service)</li>
 * <li>{@code INVENTORY}– Kho hàng, tồn kho (inventory-service)</li>
 * <li>{@code ORDER} – Đơn hàng (order-service)</li>
 * <li>{@code PAYMENT} – Giao dịch thanh toán (payment-service)</li>
 * <li>{@code LOYALTY} – Điểm tích lũy, phần thưởng (auth-service)</li>
 * <li>{@code VOUCHER} – Mã giảm giá, voucher (auth-service)</li>
 * <li>{@code AFFILIATE}– CTV, hoa hồng (affiliate-service)</li>
 * <li>{@code INVOICE} – Hóa đơn điện tử (einvoice-service)</li>
 * <li>{@code NOTIFICATION}– Thông báo (notification-service)</li>
 * <li>{@code BOOKING} – Đặt lịch dịch vụ (booking-service)</li>
 * <li>{@code RESOURCE}– Quản lý tài nguyên (resource-service)</li>
 * <li>{@code CATALOG} – Danh mục dịch vụ (service-catalog-service)</li>
 * <li>{@code SCHEDULE}– Lịch làm việc (schedule-service)</li>
 * <li>{@code DATA} – Ràng buộc dữ liệu</li>
 * <li>{@code SYSTEM} – Lỗi hệ thống / hạ tầng</li>
 * </ul>
 */
public enum ApiErrorCode {

    // =========================================================================
    // COMMON – Lỗi chung, không thuộc domain cụ thể
    // =========================================================================
    VALIDATION_FAILED("COMMON.VALIDATION_FAILED"),
    BAD_REQUEST("COMMON.BAD_REQUEST"),
    NOT_FOUND("COMMON.NOT_FOUND"),
    CONFLICT("COMMON.CONFLICT"),
    UNPROCESSABLE_ENTITY("COMMON.UNPROCESSABLE_ENTITY"),
    TOO_MANY_REQUESTS("COMMON.TOO_MANY_REQUESTS"),
    INTERNAL_SERVER_ERROR("COMMON.INTERNAL_SERVER_ERROR"),

    // =========================================================================
    // AUTH – Xác thực & phiên đăng nhập (auth-service)
    // =========================================================================
    /** Chưa xác thực – thiếu hoặc không đọc được token */
    UNAUTHORIZED("AUTH.UNAUTHORIZED"),
    /** Access token đã hết hạn */
    AUTH_TOKEN_EXPIRED("AUTH.TOKEN_EXPIRED"),
    /** Token không hợp lệ (sai chữ ký, sai format) */
    AUTH_TOKEN_INVALID("AUTH.TOKEN_INVALID"),
    /** Token đã bị thu hồi (logout / blacklist) */
    AUTH_TOKEN_REVOKED("AUTH.TOKEN_REVOKED"),
    /** Refresh token không hợp lệ hoặc đã hết hạn */
    AUTH_REFRESH_TOKEN_INVALID("AUTH.REFRESH_TOKEN_INVALID"),
    /** Sai email hoặc password */
    AUTH_CREDENTIALS_INVALID("AUTH.CREDENTIALS_INVALID"),
    /** Email chưa được đăng ký */
    AUTH_EMAIL_NOT_FOUND("AUTH.EMAIL_NOT_FOUND"),
    /** Số điện thoại chưa được đăng ký */
    AUTH_PHONE_NOT_FOUND("AUTH.PHONE_NOT_FOUND"),
    /** Số CCCD chưa được đăng ký */
    AUTH_CITIZEN_ID_NOT_FOUND("AUTH.CITIZEN_ID_NOT_FOUND"),
    /** Mật khẩu không chính xác */
    AUTH_PASSWORD_INCORRECT("AUTH.PASSWORD_INCORRECT"),
    /** Tài khoản bị vô hiệu hóa */
    AUTH_ACCOUNT_INACTIVE("AUTH.ACCOUNT_INACTIVE"),
    /** Tài khoản bị khóa tạm thời do sai mật khẩu quá nhiều lần */
    AUTH_ACCOUNT_LOCKED("AUTH.ACCOUNT_LOCKED"),
    /** OTP sai */
    AUTH_OTP_INVALID("AUTH.OTP_INVALID"),
    /** OTP đã hết hiệu lực */
    AUTH_OTP_EXPIRED("AUTH.OTP_EXPIRED"),
    /** Tạm khóa xác thực OTP do nhập sai quá nhiều lần */
    AUTH_OTP_LOCKED("AUTH.OTP_LOCKED"),
    /** Không thể xác thực OTP qua dịch vụ Auth */
    AUTH_OTP_VERIFICATION_UNAVAILABLE("AUTH.OTP_VERIFICATION_UNAVAILABLE"),
    /** Không đủ quyền thực hiện thao tác */
    AUTH_PERMISSION_DENIED("AUTH.PERMISSION_DENIED"),
    /** Không thể tự thay đổi trạng thái của chính mình */
    AUTH_SELF_UPDATE_DENIED("AUTH.SELF_UPDATE_DENIED"),
    /** Chỉ Super Admin mới được thao tác trên nhân sự hệ thống */
    AUTH_STAFF_UPDATE_DENIED("AUTH.STAFF_UPDATE_DENIED"),
    /** Không có quyền quản lý người dùng tenant */
    AUTH_TENANT_UPDATE_DENIED("AUTH.TENANT_UPDATE_DENIED"),
    /** Tài khoản chưa đặt PIN cho POS */
    AUTH_PIN_NOT_SET("AUTH.PIN_NOT_SET"),
    /** PIN xác thực POS không đúng */
    AUTH_PIN_INVALID("AUTH.PIN_INVALID"),

    // =========================================================================
    // ACCESS – Kiểm soát truy cập, phân vùng Tenant & Module
    // =========================================================================
    /** Bị chặn truy cập (role không phù hợp) */
    FORBIDDEN("ACCESS.FORBIDDEN"),
    /** X-Tenant-Id trong header không khớp với bối cảnh */
    ACCESS_TENANT_MISMATCH("ACCESS.TENANT_MISMATCH"),
    /** Module chưa được kích hoạt cho tenant này */
    ACCESS_MODULE_DISABLED("ACCESS.MODULE_DISABLED"),

    // =========================================================================
    // TENANT – Quản lý tenant (tenant-service)
    // =========================================================================
    /** Tenant không tồn tại */
    TENANT_NOT_FOUND("TENANT.NOT_FOUND"),
    /** Hồ sơ pháp lý chưa được thiết lập */
    TENANT_LEGAL_PROFILE_NOT_FOUND("TENANT.LEGAL_PROFILE_NOT_FOUND"),
    /** Slug đã được sử dụng bởi tenant khác */
    TENANT_SLUG_TAKEN("TENANT.SLUG_TAKEN"),
    /** Mã tỉnh/thành phố không hợp lệ */
    TENANT_INVALID_PROVINCE_CODE("TENANT.INVALID_PROVINCE_CODE"),
    /** Mã phường/xã không hợp lệ theo tỉnh/thành phố đã chọn */
    TENANT_INVALID_WARD_CODE("TENANT.INVALID_WARD_CODE"),
    /** Loại hình kinh doanh không hợp lệ */
    TENANT_INVALID_BUSINESS_TYPE("TENANT.INVALID_BUSINESS_TYPE"),
    /** Gói dịch vụ của tenant đã hết hạn */
    TENANT_PLAN_EXPIRED("TENANT.PLAN_EXPIRED"),
    /** Custom domain bị trùng với tenant khác */
    TENANT_DOMAIN_CONFLICT("TENANT.DOMAIN_CONFLICT"),
    /** Tenant đang ở trạng thái không hoạt động */
    TENANT_INACTIVE("TENANT.INACTIVE"),
    /** Referral cần được user xem lại trước khi tiếp tục onboarding */
    TENANT_REFERRAL_REVIEW_REQUIRED("TENANT.REFERRAL_REVIEW_REQUIRED"),
    /** Nhóm thuế không tồn tại */
    TAX_GROUP_NOT_FOUND("TENANT.TAX_GROUP_NOT_FOUND"),
    /** Ngành nghề kinh doanh không tồn tại */
    BUSINESS_SECTOR_NOT_FOUND("TENANT.BUSINESS_SECTOR_NOT_FOUND"),
    /** Ngành nghề thuế không tồn tại */
    TAX_SECTOR_NOT_FOUND("TENANT.TAX_SECTOR_NOT_FOUND"),
    /** Tỷ lệ VAT không hợp lệ */
    INVALID_VAT_RATE("TENANT.INVALID_VAT_RATE"),
    /** Tỷ lệ PIT không hợp lệ */
    INVALID_PIT_RATE("TENANT.INVALID_PIT_RATE"),
    /** Ngưỡng doanh thu không hợp lệ */
    INVALID_REVENUE_THRESHOLD("TENANT.INVALID_REVENUE_THRESHOLD"),
    /** Ngưỡng cảnh báo doanh thu không hợp lệ */
    INVALID_REVENUE_WARNING_THRESHOLD("TENANT.INVALID_REVENUE_WARNING_THRESHOLD"),
    /** Bắt buộc nhập lý do thay đổi */
    CHANGE_REASON_REQUIRED("TENANT.CHANGE_REASON_REQUIRED"),
    /** Nhóm thuế đang được sử dụng */
    TAX_GROUP_IN_USE("TENANT.TAX_GROUP_IN_USE"),
    /** Ngành nghề thuế đã tồn tại */
    TAX_SECTOR_ALREADY_EXISTS("TENANT.TAX_SECTOR_ALREADY_EXISTS"),
    /** Mã cấu hình thuế bắt buộc nhập */
    TAX_SECTOR_CODE_REQUIRED("TENANT.TAX_SECTOR_CODE_REQUIRED"),
    /** Nhóm thuế không hợp lệ hoặc không hoạt động */
    INVALID_TAX_GROUP("TENANT.INVALID_TAX_GROUP"),
    /** Đã tồn tại ngành nghề mặc định cho workspace này */
    DEFAULT_ALREADY_EXISTS("TENANT.DEFAULT_ALREADY_EXISTS"),
    /** Ngành nghề đang được sử dụng */
    TAX_SECTOR_IN_USE("TENANT.TAX_SECTOR_IN_USE"),
    /** Mã ngành nghề sai định dạng (chỉ gồm chữ hoa, số, gạch dưới) */
    INVALID_SECTOR_CODE_FORMAT("TENANT.INVALID_SECTOR_CODE_FORMAT"),
    /** Ngành nghề cha không tồn tại */
    INVALID_PARENT_SECTOR("TENANT.INVALID_PARENT_SECTOR"),
    /** Loại workspace không hợp lệ */
    INVALID_WORKSPACE_TYPE("TENANT.INVALID_WORKSPACE_TYPE"),
    /** Mô hình kinh doanh không tồn tại */
    WORKSPACE_TYPE_NOT_FOUND("TENANT.WORKSPACE_TYPE_NOT_FOUND"),
    /** Không thể cập nhật trường dữ liệu không cho phép sửa */
    IMMUTABLE_FIELD("TENANT.IMMUTABLE_FIELD"),
    /** Thiếu mô hình kinh doanh */
    WORKSPACE_TYPE_REQUIRED("TENANT.WORKSPACE_TYPE_REQUIRED"),
    /** Thiếu nhóm ngành nghề tính thuế */
    TAX_GROUP_REQUIRED("TENANT.TAX_GROUP_REQUIRED"),
    /** Thiếu ngày bắt đầu hiệu lực */
    EFFECTIVE_START_DATE_REQUIRED("TENANT.EFFECTIVE_START_DATE_REQUIRED"),
    /** Ngày kết thúc không hợp lệ */
    INVALID_EFFECTIVE_END_DATE("TENANT.INVALID_EFFECTIVE_END_DATE"),
    /** Khoảng thời gian bị chồng lấn */
    TAX_SECTOR_OVERLAPPED("TENANT.TAX_SECTOR_OVERLAPPED"),
    /** Thời gian hiệu lực của nhóm thuế bị chồng lấn */
    TAX_GROUP_OVERLAPPED("TENANT.TAX_GROUP_OVERLAPPED"),

    // =========================================================================
    // USER – Tài khoản nhân viên / người dùng hệ thống (auth-service)
    // =========================================================================
    /** Nhân viên / người dùng không tồn tại */
    USER_NOT_FOUND("USER.NOT_FOUND"),
    /** Vai trò hệ thống không tồn tại */
    USER_ROLE_NOT_FOUND("USER.ROLE_NOT_FOUND"),
    /** Email không được để trống */
    USER_EMAIL_REQUIRED("USER.EMAIL_REQUIRED"),
    /** Email không hợp lệ */
    USER_EMAIL_INVALID("USER.EMAIL_INVALID"),
    /** Email đã được đăng ký */
    USER_EMAIL_TAKEN("USER.EMAIL_TAKEN"),
    /** Số điện thoại không được để trống */
    USER_PHONE_REQUIRED("USER.PHONE_REQUIRED"),
    /** Số điện thoại không hợp lệ */
    USER_PHONE_INVALID("USER.PHONE_INVALID"),
    /** Số điện thoại đã được đăng ký */
    USER_PHONE_TAKEN("USER.PHONE_TAKEN"),
    /** CCCD/CMND đã được đăng ký */
    USER_CITIZEN_ID_TAKEN("USER.CITIZEN_ID_TAKEN"),
    /** Tài khoản nhân viên bị khóa */
    USER_INACTIVE("USER.INACTIVE"),
    /** Vai trò được yêu cầu không hợp lệ */
    USER_ROLE_INVALID("USER.ROLE_INVALID"),

    // =========================================================================
    // CUSTOMER – Hồ sơ khách hàng (auth-service / CRM)
    // =========================================================================
    /** Khách hàng không tồn tại */
    CUSTOMER_NOT_FOUND("CUSTOMER.NOT_FOUND"),
    /** Số điện thoại khách hàng đã tồn tại trong tenant */
    CUSTOMER_PHONE_TAKEN("CUSTOMER.PHONE_TAKEN"),
    /** Email khách hàng đã tồn tại trong tenant */
    CUSTOMER_EMAIL_TAKEN("CUSTOMER.EMAIL_TAKEN"),

    // =========================================================================
    // PRODUCT – Sản phẩm, danh mục, thương hiệu (product-service)
    // =========================================================================
    /** Sản phẩm không tồn tại */
    PRODUCT_NOT_FOUND("PRODUCT.NOT_FOUND"),
    /** SKU đã được sử dụng trong tenant */
    PRODUCT_SKU_TAKEN("PRODUCT.SKU_TAKEN"),
    /** Sản phẩm đã ngừng kinh doanh */
    PRODUCT_INACTIVE("PRODUCT.INACTIVE"),
    /** Danh mục sản phẩm không tồn tại */
    PRODUCT_CATEGORY_NOT_FOUND("PRODUCT.CATEGORY_NOT_FOUND"),
    /** Slug danh mục đã tồn tại */
    PRODUCT_CATEGORY_SLUG_TAKEN("PRODUCT.CATEGORY_SLUG_TAKEN"),
    /** Thương hiệu không tồn tại */
    PRODUCT_BRAND_NOT_FOUND("PRODUCT.BRAND_NOT_FOUND"),
    /** Biến thể sản phẩm không tồn tại */
    PRODUCT_VARIANT_NOT_FOUND("PRODUCT.VARIANT_NOT_FOUND"),

    // =========================================================================
    // INVENTORY – Kho hàng & tồn kho (inventory-service)
    // =========================================================================
    /** Mục kho không tồn tại */
    INVENTORY_NOT_FOUND("INVENTORY.NOT_FOUND"),
    /** Tồn kho không đủ để thực hiện giao dịch */
    INVENTORY_INSUFFICIENT_STOCK("INVENTORY.INSUFFICIENT_STOCK"),
    /** Tenant không cho phép tồn kho âm */
    INVENTORY_NEGATIVE_STOCK_DENIED("INVENTORY.NEGATIVE_STOCK_DENIED"),
    /** Phiếu điều chỉnh kho đã được xử lý, không thể thay đổi */
    INVENTORY_ADJUSTMENT_ALREADY_PROCESSED("INVENTORY.ALREADY_ADJUSTED"),
    /** Nhà cung cấp (supplier) không tồn tại */
    INVENTORY_SUPPLIER_NOT_FOUND("INVENTORY.SUPPLIER_NOT_FOUND"),

    // =========================================================================
    // ORDER – Quản lý đơn hàng (order-service)
    // =========================================================================
    /** Đơn hàng không tồn tại */
    ORDER_NOT_FOUND("ORDER.NOT_FOUND"),
    /** Đơn hàng không thể hủy ở trạng thái hiện tại */
    ORDER_CANNOT_CANCEL("ORDER.CANNOT_CANCEL"),
    /** Đơn hàng không thể xác nhận ở trạng thái hiện tại */
    ORDER_CANNOT_CONFIRM("ORDER.CANNOT_CONFIRM"),
    /** Trạng thái thanh toán không khớp với điều kiện */
    ORDER_PAYMENT_MISMATCH("ORDER.PAYMENT_MISMATCH"),
    /** Chuyển trạng thái đơn hàng không hợp lệ */
    ORDER_INVALID_STATUS_TRANSITION("ORDER.INVALID_STATUS_TRANSITION"),
    /** Đơn hàng trống – không có sản phẩm */
    ORDER_EMPTY("ORDER.EMPTY"),
    /** Kênh bán hàng không hợp lệ */
    ORDER_CHANNEL_INVALID("ORDER.CHANNEL_INVALID"),

    // =========================================================================
    // PAYMENT – Giao dịch thanh toán (payment-service)
    // =========================================================================
    /** Giao dịch không tồn tại */
    PAYMENT_NOT_FOUND("PAYMENT.NOT_FOUND"),
    /** Lỗi từ cổng thanh toán bên thứ ba */
    PAYMENT_PROVIDER_ERROR("PAYMENT.PROVIDER_ERROR"),
    /** Đơn hàng đã được thanh toán trước đó */
    PAYMENT_ALREADY_PAID("PAYMENT.ALREADY_PAID"),
    /** Số tiền thanh toán không khớp */
    PAYMENT_AMOUNT_MISMATCH("PAYMENT.AMOUNT_MISMATCH"),
    /** Chữ ký webhook thanh toán không hợp lệ */
    PAYMENT_SIGNATURE_INVALID("PAYMENT.SIGNATURE_INVALID"),
    /** Phương thức thanh toán không được hỗ trợ */
    PAYMENT_METHOD_UNSUPPORTED("PAYMENT.METHOD_UNSUPPORTED"),
    /** GtelPay: Lỗi tạo tài khoản ảo */
    PAYMENT_GTELPAY_CREATE_VA_FAILED("PAYMENT.GTELPAY_CREATE_VA_FAILED"),
    /** GtelPay: Merchant không hợp lệ */
    PAYMENT_GTELPAY_MERCHANT_INVALID("PAYMENT.GTELPAY_MERCHANT_INVALID"),
    /** GtelPay: Token không hợp lệ */
    PAYMENT_GTELPAY_TOKEN_INVALID("PAYMENT.GTELPAY_TOKEN_INVALID"),

    // =========================================================================
    // LOYALTY – Điểm tích lũy & phần thưởng (auth-service)
    // =========================================================================
    /** Không đủ điểm để đổi thưởng */
    LOYALTY_INSUFFICIENT_POINTS("LOYALTY.INSUFFICIENT_POINTS"),
    /** Phần thưởng không tồn tại */
    LOYALTY_REWARD_NOT_FOUND("LOYALTY.REWARD_NOT_FOUND"),
    /** Phần thưởng đã hết */
    LOYALTY_REWARD_OUT_OF_STOCK("LOYALTY.REWARD_OUT_OF_STOCK"),
    /** Khách hàng đã đổi thưởng này rồi (giới hạn 1 lần) */
    LOYALTY_ALREADY_REDEEMED("LOYALTY.ALREADY_REDEEMED"),
    /** Cấu hình chương trình tích điểm chưa được thiết lập */
    LOYALTY_CONFIG_NOT_FOUND("LOYALTY.CONFIG_NOT_FOUND"),

    // =========================================================================
    // VOUCHER – Mã giảm giá (auth-service)
    // =========================================================================
    /** Voucher không tồn tại */
    VOUCHER_NOT_FOUND("VOUCHER.NOT_FOUND"),
    /** Voucher đã hết hạn */
    VOUCHER_EXPIRED("VOUCHER.EXPIRED"),
    /** Voucher đã được sử dụng hết lượt */
    VOUCHER_ALREADY_USED("VOUCHER.ALREADY_USED"),
    /** Voucher đã đạt giới hạn tổng số lượt sử dụng */
    VOUCHER_USAGE_LIMIT_REACHED("VOUCHER.USAGE_LIMIT_REACHED"),
    /** Giá trị đơn hàng chưa đạt mức tối thiểu áp dụng voucher */
    VOUCHER_MIN_ORDER_NOT_MET("VOUCHER.MIN_ORDER_NOT_MET"),
    /** Mã voucher đã tồn tại */
    VOUCHER_CODE_TAKEN("VOUCHER.CODE_TAKEN"),

    // =========================================================================
    // AFFILIATE – Cộng tác viên & hoa hồng (affiliate-service)
    // =========================================================================
    /** CTV không tồn tại */
    AFFILIATE_NOT_FOUND("AFFILIATE.NOT_FOUND"),
    /** Nhân sự không hợp lệ */
    AFFILIATE_INVALID_STAFF("AFFILIATE.INVALID_STAFF"),
    /** Hồ sơ CTV không tồn tại (chưa tạo) */
    AFFILIATE_PROFILE_NOT_FOUND("AFFILIATE.PROFILE_NOT_FOUND"),
    /** Chiến dịch không tồn tại hoặc không hoạt động */
    AFFILIATE_INVALID_CAMPAIGN("AFFILIATE.INVALID_CAMPAIGN"),
    /** Số tiền rút dưới mức tối thiểu cho phép */
    AFFILIATE_PAYOUT_BELOW_MIN("AFFILIATE.PAYOUT_BELOW_MIN"),
    /** Số tiền rút không hợp lệ */
    AFFILIATE_INVALID_PAYOUT_AMOUNT("AFFILIATE.INVALID_PAYOUT_AMOUNT"),
    /** Số dư hoa hồng khả dụng không đủ */
    AFFILIATE_INSUFFICIENT_BALANCE("AFFILIATE.INSUFFICIENT_BALANCE"),
    /** Yêu cầu rút tiền đang được xử lý */
    AFFILIATE_PAYOUT_PENDING("AFFILIATE.PAYOUT_PENDING"),
    /** Tên chiến dịch không được để trống */
    AFFILIATE_CAMPAIGN_NAME_REQUIRED("AFFILIATE.CAMPAIGN_NAME_REQUIRED"),
    /** Loại hoa hồng không được để trống */
    AFFILIATE_CAMPAIGN_COMMISSION_TYPE_REQUIRED("AFFILIATE.CAMPAIGN_COMMISSION_TYPE_REQUIRED"),
    /** Onboarding checklist ID không được để trống */
    AFFILIATE_CAMPAIGN_CHECKLIST_REQUIRED("AFFILIATE.CAMPAIGN_CHECKLIST_REQUIRED"),
    /** Onboarding checklist ID không hợp lệ */
    AFFILIATE_CAMPAIGN_INVALID_CHECKLIST_ID("AFFILIATE.CAMPAIGN_INVALID_CHECKLIST_ID"),
    /** Khóa API dịch vụ nội bộ không hợp lệ */
    AFFILIATE_INVALID_INTERNAL_SERVICE_KEY("AFFILIATE.INVALID_INTERNAL_SERVICE_KEY"),
    /** File import không đúng định dạng */
    AFFILIATE_INVALID_FILE_TYPE("AFFILIATE.INVALID_FILE_TYPE"),

    // =========================================================================
    // INVOICE - Hóa đơn điện tử (einvoice-service)
    // =========================================================================
    /** Hóa đơn không tồn tại */
    INVOICE_NOT_FOUND("INVOICE.NOT_FOUND"),
    /** Hóa đơn đã được phát hành, không thể thay đổi nội dung */
    INVOICE_ALREADY_ISSUED("INVOICE.ALREADY_ISSUED"),
    /** Chỉ có thể phát hành hóa đơn ở trạng thái bản nháp hoặc đã ký */
    INVOICE_PUBLISH_INVALID_STATUS("INVOICE.PUBLISH_INVALID_STATUS"),
    /** Không tìm thấy chứng thư */
    INVOICE_SIGN_CERT_NOT_FOUND("INVOICE.SIGN_CERT_NOT_FOUND"),
    /** Hóa đơn chưa được lưu nháp trên HILO (thiếu fkey) */
    INVOICE_MISSING_PROVIDER_ID("INVOICE.MISSING_PROVIDER_ID"),
    /** Hóa đơn chưa có Mẫu số (Pattern) */
    INVOICE_MISSING_PATTERN("INVOICE.MISSING_PATTERN"),
    /** Hóa đơn chưa có Ký hiệu (Serial) */
    INVOICE_MISSING_SERIAL("INVOICE.MISSING_SERIAL"),
    /** Hóa đơn đã bị hủy */
    INVOICE_ALREADY_CANCELLED("INVOICE.ALREADY_CANCELLED"),
    /** Hóa đơn đã bị xóa (soft-delete) */
    INVOICE_ALREADY_DELETED("INVOICE.ALREADY_DELETED"),
    /** Chỉ hóa đơn ở trạng thái Nháp mới được xóa */
    INVOICE_DELETE_ONLY_DRAFT("INVOICE.DELETE_ONLY_DRAFT"),
    /** Chỉ hóa đơn đã phát hành mới được hủy */
    INVOICE_CANCEL_ONLY_ISSUED("INVOICE.CANCEL_ONLY_ISSUED"),
    /** Lý do hủy/điều chỉnh là bắt buộc */
    INVOICE_REASON_REQUIRED("INVOICE.REASON_REQUIRED"),
    /** Chỉ hóa đơn đã phát hành mới được điều chỉnh hoặc thay thế */
    INVOICE_ADJUST_ONLY_ISSUED("INVOICE.ADJUST_ONLY_ISSUED"),
    /** Chỉ có thể chỉnh sửa hóa đơn ở trạng thái bản nháp */
    INVOICE_UPDATE_ONLY_DRAFT("INVOICE.UPDATE_ONLY_DRAFT"),
    /** Chỉ có thể thao tác trên hóa đơn đã phát hành */
    INVOICE_ACTION_ONLY_ISSUED("INVOICE.ACTION_ONLY_ISSUED"),
    /** Hóa đơn gốc để điều chỉnh/thay thế không tồn tại */
    INVOICE_ORIGINAL_NOT_FOUND("INVOICE.ORIGINAL_NOT_FOUND"),
    /** Cấu hình hóa đơn chưa được thiết lập cho tenant */
    INVOICE_CONFIG_NOT_FOUND("INVOICE.CONFIG_NOT_FOUND"),
    /** Cấu hình kết nối HILO chưa được kích hoạt */
    INVOICE_PROVIDER_NOT_CONFIGURED("INVOICE.PROVIDER_NOT_CONFIGURED"),
    /** Lỗi từ nhà cung cấp HILO (bao gồm lỗi ký số, sai XML, sai MST...) */
    INVOICE_PROVIDER_ERROR("INVOICE.PROVIDER_ERROR"),
    /** Lỗi ký số hóa đơn */
    INVOICE_SIGN_FAILED("INVOICE.SIGN_FAILED"),
    /** File XML hóa đơn không thể tải về */
    INVOICE_XML_DOWNLOAD_FAILED("INVOICE.XML_DOWNLOAD_FAILED"),
    /** Tenant chưa xác thực hộ kinh doanh, không được dùng module hóa đơn */
    INVOICE_TENANT_NOT_VERIFIED("INVOICE.TENANT_NOT_VERIFIED"),
    /** Lỗi khi call api Hilo */
    EXTERNAL_API_ERROR("INVOICE.EXTERNAL_API_ERROR"),
    /** Ký Token không hoạt động */
    INVOICE_TOKEN_NOT_WORKING("INVOICE.TOKEN_NOT_WORKING"),

    // =========================================================================
    // NOTIFICATION – Thông báo (notification-service)
    // =========================================================================
    /** Gửi thông báo thất bại */
    NOTIFICATION_SEND_FAILED("NOTIFICATION.SEND_FAILED"),
    /** Template thông báo không tồn tại */
    NOTIFICATION_TEMPLATE_NOT_FOUND("NOTIFICATION.TEMPLATE_NOT_FOUND"),

    // =========================================================================
    // BOOKING – Đặt lịch dịch vụ (booking-service)
    // =========================================================================
    /** Lịch đặt không tồn tại */
    BOOKING_NOT_FOUND("BOOKING.NOT_FOUND"),
    /** Khung giờ đã được đặt */
    BOOKING_SLOT_UNAVAILABLE("BOOKING.SLOT_UNAVAILABLE"),
    /** Tài nguyên liên kết không khả dụng */
    BOOKING_RESOURCE_UNAVAILABLE("BOOKING.RESOURCE_UNAVAILABLE"),
    /** Không thể hủy lịch ở trạng thái hiện tại */
    BOOKING_CANNOT_CANCEL("BOOKING.CANNOT_CANCEL"),
    /** Lịch đặt đã được xác nhận */
    BOOKING_ALREADY_CONFIRMED("BOOKING.ALREADY_CONFIRMED"),

    // =========================================================================
    // RESOURCE – Quản lý tài nguyên (resource-service)
    // =========================================================================
    /** Tài nguyên không tồn tại */
    RESOURCE_NOT_FOUND("RESOURCE.NOT_FOUND"),
    /** Tài nguyên đang được sử dụng, không thể xóa/vô hiệu hóa */
    RESOURCE_ALREADY_IN_USE("RESOURCE.ALREADY_IN_USE"),
    /** Loại tài nguyên không tồn tại */
    RESOURCE_TYPE_NOT_FOUND("RESOURCE.TYPE_NOT_FOUND"),

    // =========================================================================
    // CATALOG – Danh mục dịch vụ (service-catalog-service)
    // =========================================================================
    /** Dịch vụ trong danh mục không tồn tại */
    CATALOG_SERVICE_NOT_FOUND("CATALOG.NOT_FOUND"),
    /** Slug dịch vụ đã tồn tại */
    CATALOG_SLUG_TAKEN("CATALOG.SLUG_TAKEN"),

    // =========================================================================
    // SCHEDULE – Lịch làm việc / lịch dịch vụ (schedule-service)
    // =========================================================================
    /** Lịch bị xung đột với lịch đã tồn tại */
    SCHEDULE_CONFLICT("SCHEDULE.CONFLICT"),
    /** Khoảng thời gian không hợp lệ */
    SCHEDULE_INVALID_RANGE("SCHEDULE.INVALID_RANGE"),

    // =========================================================================
    // AI – Dịch vụ AI / OpenRouter
    // =========================================================================
    /** Thiếu user context để tính hạn mức AI Credit */
    AI_USER_CONTEXT_REQUIRED("AI.USER_CONTEXT_REQUIRED"),
    /** User đã dùng vượt token AI Credit trong ngày */
    AI_TOKEN_LIMIT_EXCEEDED("AI.TOKEN_LIMIT_EXCEEDED"),
    /** Ảnh OCR không đọc được hoặc định dạng chưa hỗ trợ */
    AI_IMAGE_UNSUPPORTED("AI.IMAGE_UNSUPPORTED"),
    /** Ảnh OCR vẫn vượt giới hạn sau khi tối ưu */
    AI_IMAGE_TOO_LARGE("AI.IMAGE_TOO_LARGE"),

    // =========================================================================
    // REFERRAL – Mã giới thiệu (auth-service)
    // =========================================================================
    /** Mã giới thiệu đã tồn tại trong hệ thống */
    REFERRAL_CODE_EXISTS("REFERRAL.CODE_EXISTS"),

    // =========================================================================
    // EKYB – Xác thực doanh nghiệp (tenant-service)
    // =========================================================================
    /** Giấy phép kinh doanh không có mã số thuế */
    EKYB_NO_TAX_CODE("EKYB.NO_TAX_CODE"),
    /** Giấy phép kinh doanh không hợp lệ hoặc không đọc được */
    EKYB_INVALID_LICENSE("EKYB.INVALID_LICENSE"),
    /** Dịch vụ OCR bên ngoài trả lỗi */
    EKYB_UPSTREAM_ERROR("EKYB.UPSTREAM_ERROR"),

    // =========================================================================
    // DATA – Ràng buộc dữ liệu
    // =========================================================================
    /** Vi phạm ràng buộc dữ liệu (unique constraint, FK, ...) */
    DATABASE_CONSTRAINT_VIOLATION("DATA.CONSTRAINT_VIOLATION"),

    // =========================================================================
    // SYSTEM – Lỗi hệ thống / hạ tầng
    // =========================================================================
    /** Lỗi cơ sở dữ liệu */
    DATABASE_ERROR("SYSTEM.DATABASE_ERROR"),
    /** Lỗi dịch vụ bên thứ ba */
    EXTERNAL_SERVICE_ERROR("SYSTEM.EXTERNAL_SERVICE_ERROR"),
    /** Dịch vụ không khả dụng tạm thời */
    SERVICE_UNAVAILABLE("SYSTEM.SERVICE_UNAVAILABLE"),
    /** Lỗi nội bộ không xác định */
    INTERNAL_ERROR("SYSTEM.INTERNAL_ERROR");

    // =========================================================================

    private final String value;

    ApiErrorCode(String value) {
        this.value = value;
    }

    /** Trả về chuỗi mã lỗi dùng trong API response (VD: "ORDER.NOT_FOUND"). */
    public String value() {
        return value;
    }

    /**
     * Trả về mã lỗi mặc định tương ứng với HTTP status.
     * Dùng khi không có mã cụ thể hơn.
     */
    public static String defaultForStatus(HttpStatus status) {
        return switch (status) {
            case BAD_REQUEST -> BAD_REQUEST.value();
            case UNAUTHORIZED -> UNAUTHORIZED.value();
            case FORBIDDEN -> FORBIDDEN.value();
            case NOT_FOUND -> NOT_FOUND.value();
            case CONFLICT -> CONFLICT.value();
            case UNPROCESSABLE_ENTITY -> UNPROCESSABLE_ENTITY.value();
            case TOO_MANY_REQUESTS -> TOO_MANY_REQUESTS.value();
            case BAD_GATEWAY -> EXTERNAL_SERVICE_ERROR.value();
            case SERVICE_UNAVAILABLE -> SERVICE_UNAVAILABLE.value();
            default -> INTERNAL_ERROR.value();
        };
    }
}
