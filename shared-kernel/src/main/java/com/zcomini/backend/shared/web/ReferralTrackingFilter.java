package com.zcomini.backend.shared.web;

import com.zcomini.backend.shared.tenant.RequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class ReferralTrackingFilter extends OncePerRequestFilter {

    private static final String REF_PARAM = "ref";
    private static final String REF_COOKIE = "fz_ref";
    private static final int COOKIE_MAX_AGE = 30 * 24 * 60 * 60; // 30 days

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String referralCode = request.getParameter(REF_PARAM);

        if (StringUtils.hasText(referralCode)) {
            log.info("Found referral code in URL: {}", referralCode);
            saveReferralCookie(response, referralCode);
        } else {
            referralCode = getReferralFromCookie(request);
        }

        if (StringUtils.hasText(referralCode)) {
            RequestContext.setReferralCode(referralCode);
        }

        filterChain.doFilter(request, response);
    }

    private void saveReferralCookie(HttpServletResponse response, String code) {
        Cookie cookie = new Cookie(REF_COOKIE, code);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        // In production, you might want cookie.setSecure(true);
        response.addCookie(cookie);
    }

    private String getReferralFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        return Arrays.stream(request.getCookies())
                .filter(c -> REF_COOKIE.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
