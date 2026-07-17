package com.zcomini.backend.shared.constant;

public final class AuditConstant {
    private AuditConstant() {
    }

    public static final class Campaign {
        public static final String CREATE = "Tạo mới chiến dịch Affiliate";
        public static final String UPDATE = "Cập nhật chiến dịch Affiliate";
        public static final String DELETE = "Xóa chiến dịch Affiliate";
        public static final String TOGGLE_STATUS = "Thay đổi trạng thái chiến dịch Affiliate";
        public static final String ASSIGN_AFFILIATE = "Phân bổ CTV cho chiến dịch Affiliate";
    }

    public static final class Affiliate {
        public static final String CREATE = "Tạo mới cộng tác viên";
        public static final String UPDATE = "Cập nhật cộng tác viên";
        public static final String DELETE = "Xóa cộng tác viên";
        public static final String TOGGLE_STATUS = "Thay đổi trạng thái cộng tác viên";
    }

    public static final class TargetType {
        public static final String CAMPAIGN = "Chiến dịch Affiliate";
        public static final String AFFILIATE = "Cộng tác viên";
    }
}
