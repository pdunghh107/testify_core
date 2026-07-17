package com.zcomini.backend.shared.message;

public final class ValidateString {
    public static final String EMAIL_REQUIRED = "Vui lòng nhập email";
    public static final String EMAIL_INVALID = "Email không đúng định dạng";

    public static final String PASSWORD_REQUIRED = "Vui lòng nhập mật khẩu";
    public static final String PASSWORD_INVALID = "Mật khẩu phải có ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số, ký tự đặc biệt và không chứa khoảng trắng.";

    public static final String FULLNAME_REQUIRED = "Vui lòng nhập họ và tên";
    public static final String FULLNAME_INVALID = "Họ và tên tối thiểu 2 ký tự và tối đa 255 ký tự";

    public static final String SYSTEM_ROLE_REQURIED = "Vui lòng chọn quyền trong hệ thống CRM Admin";
}
