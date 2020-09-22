package com.jungtin.controller;

import com.jungtin.common.ViewName;
import com.jungtin.exception.DuplicateEntityException;
import com.jungtin.service.AccountService;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterController", urlPatterns = {
    "/register"
})
public class RegisterController extends HttpServlet {
    
    private final AccountService accountService;
    
    public RegisterController() {
        this.accountService = new AccountService();
    }
    
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        processRegister(request, response);
    }

    protected void doGet(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        showRegisterForm(request, response);
    }
    /*
    *   CREATE
    * */
    private void showRegisterForm(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(ViewName.REGISTER);
        dispatcher.forward(request, response);
    }
    
    private void processRegister(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            accountService.register(request);
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (DuplicateEntityException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(ViewName.REGISTER).forward(request, response);
        }
    }
    
}
