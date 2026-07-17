package com.zcomini.backend.shared.util;

import java.util.Locale;
import java.util.regex.Pattern;

public class StringCustom {
    // Các nội dung custom utils sẽ được phát triển trong tương lai

    // 1. Chuẩn số điện thoại của VN
    public static boolean isVnPhoneNumber(String phone) {
        if (phone == null)
            return false;
        String regex = "^(0|\\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-5|8|9]|9[0-4|6-9])[0-9]{7}$";
        return Pattern.matches(regex, phone.trim());
    }

    // ----------------------------------------------------------------
    // STRING HELPERS
    // ----------------------------------------------------------------
    public static String trimOrNull(String str) {
        if (str == null) {
            return null;
        }
        String trimmed = str.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    // 1. Chuẩn hóa email
    public static String normalizeEmail(String email) {
        return (email == null || email.isBlank()) ? null : email.trim().toLowerCase(Locale.ROOT);
    }
}
