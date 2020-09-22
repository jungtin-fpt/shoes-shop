package com.jungtin.security;

import com.jungtin.common.ViewName;
import com.jungtin.model.Account;
import com.jungtin.service.AccountService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {
    
    private final AccountService accountService;
    
    public LoginController() {
        this.accountService = new AccountService();
    }
    
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        Account account = accountService.findByUsernamePassword(request);
        
        if (account == null) {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher(ViewName.LOGIN).forward(request, response);
            return;
        }
    
        UserDetails userDetails = new UserDetails(account.getId(), account.getUsername());
        HttpSession session = request.getSession();
        AppUtils.storeLoginedUser(session, userDetails);
    
        Object redirect_uri = session.getAttribute("redirect_uri");
        if (redirect_uri != null) {
            String redirectUri = redirect_uri.toString();
            session.removeAttribute("redirect_uri");
            response.sendRedirect(redirectUri);
        } else {
            // Mặc định sau khi đăng nhập thành công
            // chuyển hướng về trang / (index)
            response.sendRedirect(request.getContextPath() + "/");
        }
        
    }
    
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        request.getRequestDispatcher(ViewName.LOGIN).forward(request, response);
    }
}
