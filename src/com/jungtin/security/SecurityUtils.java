package com.jungtin.security;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {
    // Kiểm tra 'request' này có bắt buộc phải đăng nhập hay không.
    public static boolean isSecurityPage(HttpServletRequest request) {
        String requestUrl = request.getServletPath();
        List<String> protectUrls = SecurityConfig.protectUrls;
        if (protectUrls != null && !protectUrls.isEmpty() && protectUrls.contains(requestUrl))
            return true;
        
        return false;
    }
}