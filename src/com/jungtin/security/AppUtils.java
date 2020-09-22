package com.jungtin.security;

import javax.servlet.http.HttpSession;

public class AppUtils {
    
    // Lưu trữ thông tin người dùng vào Session.
    public static void storeLoginedUser(HttpSession session, UserDetails userDetails) {
        // Trên JSP có thể truy cập thông qua ${loginedUser}
        session.setAttribute("loginedUser", userDetails);
    }
    
    // Lấy thông tin người dùng lưu trữ trong Session.
    public static UserDetails getLoginedUser(HttpSession session) {
        UserDetails userDetails = (UserDetails) session.getAttribute("loginedUser");
        return userDetails;
    }
}
