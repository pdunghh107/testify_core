package com.zcomini.backend.shared.api;

/**
 * Registry chứa tất cả các mã kết quả thành công cho các API JSON.
 * Các mã này đóng vai trò là key chuẩn để Frontend thực hiện i18n.
 * 
 * Quy tắc đặt tên: {DOMAIN}_{EVENT} (VD: PAYMENT_INITIATED)
 * Giá trị chuỗi tương ứng: {DOMAIN}.{EVENT} (VD: "PAYMENT.INITIATED")
 */
public enum ApiSuccessCode {

    SUCCESS("COMMON.SUCCESS"),

    // ==========================================
    // TENANT SETTINGS DOMAIN
    // ==========================================
    TENANT_SETTINGS_FETCHED("TENANT_SETTINGS.FETCHED"),
    TENANT_SETTINGS_UPDATED("TENANT_SETTINGS.UPDATED"),

    // ==========================================
    // PAYMENT DOMAIN
    // ==========================================
    PAYMENT_FETCHED("PAYMENT.FETCHED"),
    PAYMENT_LISTED("PAYMENT.LISTED"),
    PAYMENT_INITIATED("PAYMENT.INITIATED"),
    PAYMENT_STATUS_UPDATED("PAYMENT.STATUS_UPDATED"),
    PAYMENT_CANCELLED("PAYMENT.CANCELLED"),

    // ==========================================
    // AI DOMAIN
    // ==========================================
    AI_QR_INTENT_PARSED("AI.QR_INTENT_PARSED"),

    // ==========================================
    // INVOICE DOMAIN
    // ==========================================
    INVOICE_FETCHED("INVOICE.FETCHED"),
    INVOICE_LISTED("INVOICE.LISTED"),
    INVOICE_CREATED("INVOICE.CREATED"),
    INVOICE_UPDATED("INVOICE.UPDATED"),
    INVOICE_DELETED("INVOICE.DELETED"),
    INVOICE_SIGNED("INVOICE.SIGNED"),
    INVOICE_PUBLISHED("INVOICE.PUBLISHED"),
    INVOICE_DIRECT_ISSUED("INVOICE.DIRECT_ISSUED"),
    INVOICE_CANCELLED("INVOICE.CANCELLED"),
    INVOICE_REPLACED("INVOICE.REPLACED"),

    // ==========================================
    // AUTH DOMAIN
    // ==========================================
    AUTH_LOGGED_IN("AUTH.LOGGED_IN"),
    AUTH_FETCHED("AUTH.FETCHED"),
    AUTH_PROFILE_UPDATED("AUTH.PROFILE_UPDATED"),
    AUTH_ACCOUNT_DELETED("AUTH.ACCOUNT_DELETED"),
    /** Gửi OTP thành công (forgot-password, otp/request) */
    AUTH_OTP_SENT("AUTH.OTP_SENT"),
    /** Đặt lại mật khẩu thành công */
    AUTH_PASSWORD_RESET("AUTH.PASSWORD_RESET"),
    /** Xác minh OTP thành công (otp/verify) */
    AUTH_OTP_VERIFIED("AUTH.OTP_VERIFIED"),

    // ==========================================
    // IAM DOMAIN
    // ==========================================
    IAM_USER_LISTED("IAM.USER_LISTED"),
    IAM_USER_CREATED("IAM.USER_CREATED"),
    IAM_CRM_USER_CREATED("IAM.CRM_USER_CREATED"),
    IAM_USER_UPDATED("IAM.USER_UPDATED"),
    IAM_USER_STATUS_TOGGLED("IAM.USER_STATUS_TOGGLED"),
    IAM_CRM_USER_STATUS_TOGGLED("IAM.CRM_USER_STATUS_TOGGLED"),

    IAM_USER_PASSWORD_RESET("IAM.USER_PASSWORD_RESET"),
    IAM_ROLE_LISTED("IAM.ROLE_LISTED"),
    IAM_ROLE_CREATED("IAM.ROLE_CREATED"),
    IAM_ROLE_UPDATED("IAM.ROLE_UPDATED"),
    IAM_ROLE_STATUS_UPDATED("IAM.ROLE_STATUS_UPDATED"),
    IAM_MODULE_LISTED("IAM.MODULE_LISTED"),
    IAM_PERMISSION_UPDATED("IAM.PERMISSION_UPDATED"),
    IAM_PERMISSION_LISTED("IAM.PERMISSION_LISTED"),
    IAM_AUDIT_LOG_LISTED("IAM.AUDIT_LOG_LISTED"),

    // ==========================================
    // TENANT DOMAIN
    // ==========================================
    TENANT_LISTED("TENANT.LISTED"),
    TENANT_STATS_FETCHED("TENANT.STATS_FETCHED"),
    TENANT_FETCHED("TENANT.FETCHED"),
    TENANT_CREATED("TENANT.CREATED"),
    TENANT_UPDATED("TENANT.UPDATED"),
    TENANT_STATUS_UPDATED("TENANT.STATUS_UPDATED"),
    TENANT_ASSIGNED("TENANT.ASSIGNED"),
    TENANT_TAX_PROFILE_FETCHED("TENANT.TAX_PROFILE_FETCHED"),
    TENANT_APPROVED("TENANT.APPROVED"),
    TENANT_REJECTED("TENANT.REJECTED"),

    // Tax Config
    TAX_ESTIMATED("TAX.ESTIMATED"),

    // -- 10 api giong nhau
    TAX_GROUP_LISTED("TAX.TAX_GROUP_LISTED"),
    TAX_GROUP_DETAILED("TAX.TAX_GROUP_DETAILED"),
    TAX_GROUP_CREATED("TAX.TAX_GROUP_CREATED"),
    TAX_GROUP_UPDATED("TAX.TAX_GROUP_UPDATED"),
    TAX_GROUP_CHECKED("TAX.TAX_GROUP_CHECKED"),

    TAX_CONFIG_LISTED("TENANT.TAX_CONFIG_LISTED"),
    TAX_CONFIG_DETAILED("TENANT.TAX_CONFIG_DETAILED"),
    TAX_CONFIG_CREATED("TENANT.TAX_CONFIG_CREATED"),
    TAX_CONFIG_UPDATED("TENANT.TAX_CONFIG_UPDATED"),
    TAX_CONFIG_CHECKED("TENANT.TAX_CONFIG_CHECKED"),

    // -- 2 api them cho config
    TAX_CONFIG_STATED("TENANT.TAX_CONFIG_STATED"),
    TAX_CONFIG_EXPORTED("TENANT.TAX_CONFIG_EXPORTED"),

    // -- 1 api list log
    TAX_CONFIG_LOG("TENANT.TAX_CONFIG_LOG"),

    // -- 3 api options
    TAX_WORKSPACE_OPTIONS("TENANT.TAX_WORKSPACE_OPTIONS"),
    TAX_GROUPS_OPTIONS("TENANT.TAX_GROUPS_OPTIONS"),
    TAX_CONFIG_OPTIONS("TENANT.TAX_CONFIG_OPTIONS"),

    // ==========================================
    // SUPPORT DOMAIN
    // ==========================================
    SUPPORT_TICKET_LISTED("SUPPORT.TICKET_LISTED"),
    SUPPORT_TICKET_FETCHED("SUPPORT.TICKET_FETCHED"),
    SUPPORT_MESSAGE_LISTED("SUPPORT.MESSAGE_LISTED"),
    SUPPORT_STAFF_ASSIGNED("SUPPORT.STAFF_ASSIGNED"),
    SUPPORT_STATUS_UPDATED("SUPPORT.STATUS_UPDATED"),
    SUPPORT_REPLIED("SUPPORT.REPLIED"),
    SUPPORT_QUICK_LOG_CREATED("SUPPORT.QUICK_LOG_CREATED"),

    // ==========================================
    // ORDER DOMAIN
    // ==========================================
    ORDER_LISTED("ORDER.LISTED"),
    ORDER_FETCHED("ORDER.FETCHED"),
    ORDER_STATUS_UPDATED("ORDER.STATUS_UPDATED"),

    // ==========================================
    // BLOG DOMAIN
    // ==========================================
    BLOG_LISTED("BLOG.LISTED"),
    BLOG_FETCHED("BLOG.FETCHED"),
    BLOG_CREATED("BLOG.CREATED"),
    BLOG_UPDATED("BLOG.UPDATED"),
    BLOG_STATUS_UPDATED("BLOG.STATUS_UPDATED"),
    BLOG_DELETED("BLOG.DELETED"),

    // ==========================================
    // NOTIFICATION DOMAIN
    // ==========================================
    NOTIFICATION_MESSAGE_QUEUED("NOTIFICATION.MESSAGE_QUEUED"),
    NOTIFICATION_TEMPLATE_FETCHED("NOTIFICATION.TEMPLATE_FETCHED"),
    NOTIFICATION_TEMPLATE_CREATED("NOTIFICATION.TEMPLATE_CREATED"),
    NOTIFICATION_TEMPLATE_UPDATED("NOTIFICATION.TEMPLATE_UPDATED"),

    // ==========================================
    // AFFILIATE DOMAIN
    // ==========================================
    AFFILIATE_STATS_FETCHED("AFFILIATE.STATS_FETCHED"),
    AFFILIATE_WALLET_FETCHED("AFFILIATE.WALLET_FETCHED"),
    AFFILIATE_LISTED("AFFILIATE.LISTED"),
    AFFILIATE_CREATED("AFFILIATE.CREATED"),
    AFFILIATE_UPDATED("AFFILIATE.UPDATED"),
    AFFILIATE_STATUS_TOGGLED("AFFILIATE.STATUS_TOGGLED"),
    AFFILIATE_CAMPAIGN_LISTED("AFFILIATE.CAMPAIGN_LISTED"),
    AFFILIATE_CAMPAIGN_FETCHED("AFFILIATE.CAMPAIGN_FETCHED"),
    AFFILIATE_CAMPAIGN_CREATED("AFFILIATE.CAMPAIGN_CREATED"),
    AFFILIATE_CAMPAIGN_UPDATED("AFFILIATE.CAMPAIGN_UPDATED"),
    AFFILIATE_PAYOUT_LISTED("AFFILIATE.PAYOUT_LISTED"),
    AFFILIATE_PAYOUT_APPROVED("AFFILIATE.PAYOUT_APPROVED"),
    AFFILIATE_PAYOUT_REJECTED("AFFILIATE.PAYOUT_REJECTED"),
    AFFILIATE_FINHUB_LOAN_LEAD_CREATED("AFFILIATE.FINHUB_LOAN_LEAD_CREATED"),
    AFFILIATE_FINHUB_LOAN_LEAD_FETCHED("AFFILIATE.FINHUB_LOAN_LEAD_FETCHED"),
    AFFILIATE_FINHUB_LOAN_LEAD_UPDATED("AFFILIATE.FINHUB_LOAN_LEAD_UPDATED"),
    AFFILIATE_FINHUB_LOAN_LEAD_LISTED("AFFILIATE.FINHUB_LOAN_LEAD_LISTED"),
    AFFILIATE_CAMPAIGN_ASSIGNED("AFFILIATE.CAMPAIGN_ASSIGNED"),
    AFFILIATE_STAFF_ASSIGNED("AFFILIATE.STAFF_ASSIGNED"),
    AFFILIATE_IMPORTED("AFFILIATE.IMPORTED"),

    // ==========================================
    // PRODUCT DOMAIN
    // ==========================================
    PRODUCT_LISTED("PRODUCT.LISTED"),
    PRODUCT_FETCHED("PRODUCT.FETCHED"),

    // ==========================================
    // CUSTOMER DOMAIN
    // ==========================================
    CUSTOMER_FETCHED("CUSTOMER.FETCHED"),
    CUSTOMER_LISTED("CUSTOMER.LISTED"),
    CUSTOMER_CREATED("CUSTOMER.CREATED"),
    CUSTOMER_UPDATED("CUSTOMER.UPDATED"),
    CUSTOMER_DELETED("CUSTOMER.DELETED"),
    CUSTOMER_STATUS_UPDATED("CUSTOMER.STATUS_UPDATED"),

    // ==========================================
    // REFERRAL DOMAIN
    // ==========================================
    /** POST /code — tạo mã giới thiệu thành công */
    REFERRAL_CREATED("REFERRAL.CREATED"),
    /** GET /me, /users/{id}, /code/suggest — lấy thông tin/gợi ý */
    REFERRAL_FETCHED("REFERRAL.FETCHED"),
    /** GET /lookup — xác minh tính hợp lệ của mã giới thiệu */
    REFERRAL_VERIFIED("REFERRAL.VERIFIED"),

    /** @deprecated dùng REFERRAL_CREATED */
    REFERRAL_CODE_CREATED("REFERRAL.CODE_CREATED"),
    /** @deprecated dùng REFERRAL_FETCHED */
    REFERRAL_CODE_FETCHED("REFERRAL.CODE_FETCHED"),
    /** @deprecated dùng REFERRAL_FETCHED */
    REFERRAL_PROFILE_FETCHED("REFERRAL.PROFILE_FETCHED"),
    /** @deprecated dùng REFERRAL_VERIFIED */
    REFERRAL_LOOKUP_COMPLETED("REFERRAL.LOOKUP_COMPLETED"),

    // ==========================================
    // TESTER DOMAIN
    // ==========================================
    TESTER_USER_LISTED("TESTER_USER.LISTED"),
    TESTER_USER_UPDATED("TESTER_USER.UPDATED"),
    TESTER_USER_STATUS_UPDATED("TESTER_USER.STATUS_UPDATED"),

    // ==========================================
    // EKYB DOMAIN
    // ==========================================
    /** POST /ekyb/ocrx/decode – Đọc OCR giấy phép kinh doanh thành công */
    EKYB_OCR_DECODED("EKYB.OCR_DECODED"),
    /**
     * POST /ekyb/taxcode/verify – Xác thực mã số thuế thành công (verified hoặc
     * pending)
     */
    EKYB_TAX_CODE_VERIFIED("EKYB.TAX_CODE_VERIFIED"),

    // ==========================================
    // INVOICE CONFIG DOMAIN
    // ==========================================
    /** GET /invoice-config/provider – Lấy cấu hình provider HĐĐT */
    INVOICE_CONFIG_PROVIDER_FETCHED("INVOICE_CONFIG.PROVIDER_FETCHED"),
    /** PUT /invoice-config/provider – Lưu/cập nhật cấu hình provider HĐĐT */
    INVOICE_CONFIG_PROVIDER_UPDATED("INVOICE_CONFIG.PROVIDER_UPDATED"),
    /** POST /invoice-config/provider/test – Test kết nối nhà cung cấp HĐĐT */
    INVOICE_CONFIG_PROVIDER_TESTED("INVOICE_CONFIG.PROVIDER_TESTED"),
    /** GET /invoice-config/tax-settings – Lấy cấu hình thuế */
    INVOICE_CONFIG_TAX_FETCHED("INVOICE_CONFIG.TAX_FETCHED"),
    /** PUT /invoice-config/tax-settings – Cập nhật cấu hình thuế */
    INVOICE_CONFIG_TAX_UPDATED("INVOICE_CONFIG.TAX_UPDATED"),
    /** GET /invoice-config/certs – Lấy danh sách chứng thư số */
    INVOICE_CONFIG_CERT_LISTED("INVOICE_CONFIG.CERT_LISTED"),
    /** POST /invoice-config/certs – Tạo mới chứng thư số */
    INVOICE_CONFIG_CERT_CREATED("INVOICE_CONFIG.CERT_CREATED"),
    /** PUT /invoice-config/certs/{id}/default – Đặt chứng thư mặc định */
    INVOICE_CONFIG_CERT_DEFAULT_SET("INVOICE_CONFIG.CERT_DEFAULT_SET"),
    /** DELETE /invoice-config/certs/{id} – Xóa chứng thư số */
    INVOICE_CONFIG_CERT_DELETED("INVOICE_CONFIG.CERT_DELETED"),

    // ==========================================
    // TRANSACTION / IOC DOMAIN
    // ==========================================
    /**
     * GET /ioc/v1/transactions/summary – Tổng hợp giao dịch QR (tĩnh/động) theo kỳ
     */
    TRANSACTION_SUMMARIZED("TRANSACTION.SUMMARIZED");

    private final String value;

    ApiSuccessCode(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
