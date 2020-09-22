package com.jungtin.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    
    public SecurityFilter() {
    }
    
    @Override
    public void destroy() {
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        
        UserDetails loginedUser = AppUtils.getLoginedUser(request.getSession());
        if(loginedUser != null) { // đã đăng nhập
            chain.doFilter(request, response);
            return;
        }
    
        String servletPath = request.getServletPath();
        if (servletPath.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Các trang bắt buộc phải đăng nhập.
        if (SecurityUtils.isSecurityPage(request)) {
            // Nếu người dùng chưa đăng nhập,
            // Redirect (chuyển hướng) tới trang đăng nhập.
            if (loginedUser == null) {
                request.getSession().setAttribute("redirect_uri", request.getRequestURL().toString());
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        }
        chain.doFilter(request, response);
    }
    
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    
    }
    
}