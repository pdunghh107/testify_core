package com.zcomini.backend.shared.constant;

import java.util.UUID;

public final class SystemConstant {
    private SystemConstant() {
    }

    public static final UUID SYSTEM_TENANT_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String SUPER_ADMIN = "super_admin";
    public static final String ROOT_EMAIL = "superadmin@zcomini.local";
    public static final String DEFAULT_PASSWORD = "Admin123@";
    public static final String CRM_PREFIX = "CRM_";
    public static final String USER = "Người dùng";
}
