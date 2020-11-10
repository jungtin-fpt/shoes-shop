package com.jungtin.controller;

import com.jungtin.common.Validator;
import com.jungtin.common.ViewName;
import com.jungtin.model.Account;
import com.jungtin.service.AccountService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AccountController", urlPatterns = {
    "/accounts/*"
})
public class AccountController extends HttpServlet {
    
    private final AccountService accountService;
    private final Validator validator;
    
    public AccountController() {
        this.accountService = new AccountService();
        this.validator = new Validator();
    }
    
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getPathInfo();
        if(action == null)
            list(request, response);
        else {
            switch (action) {
                case "/process":
                    processForm(request, response);
                    break;
            }
        }
    }

    protected void doGet(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getPathInfo();
        if(action == null)
            list(request, response);
        else {
            switch (action) {
                case "/create":
                    showCreateForm(request, response);
                    break;
                case "/update":
                    showUpdateForm(request, response);
                    break;
                case "/delete":
                    delete(request, response);
                    break;
            }
        }
    }
    /*
    *   CREATE
    * */
    private void showCreateForm(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(ViewName.ACCOUNT_FORM);
        dispatcher.forward(request, response);
    }
    
    /*
    *   UPDATE
    * */
    private void showUpdateForm(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Integer id  = checkExistIdParam(request, response);
        Account account = accountService.findById(id);
        request.setAttribute("account", account);
        request.getRequestDispatcher(ViewName.ACCOUNT_FORM).forward(request, response);
    }
    
    private void processForm(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HashMap<String, String> errors = new HashMap<>();
    
        validator.checkRequired("username", request, errors);
        validator.checkRequired("password", request, errors);
    
        if(errors.size() > 0) {
            HashMap<String, String> account = new HashMap<>();
            account.put("username", request.getParameter("username"));
            account.put("password", request.getParameter("password"));
        
            request.setAttribute("account", account);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher(ViewName.ACCOUNT_FORM).forward(request, response);
            return;
        }
        
        accountService.saveOrUpdate(request);
        redirectToList(request, response);
    }
    /*
    *   DELETE
    * */
    private void delete(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Integer id = checkExistIdParam(request, response);
        accountService.delete(id);
        redirectToList(request, response);
    }
    
    private void list(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String accountId = request.getParameter("id");
        if (accountId != null) {
            getOne(request, response);
        } else
            getAll(request, response);
    }
    
    private void getOne(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Integer id = checkExistIdParam(request, response);
        Account account = accountService.findById(id);
        request.setAttribute("account", account);
        // send
    }
    
    private void getAll(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        List<Account> accounts = accountService.findAll();
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher(ViewName.ACCOUNT_LIST)
            .forward(request, response);
    }
    
    /*
    *  Tools
    * */
    private Integer checkExistIdParam(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String accountId = request.getParameter("id");
        if (accountId == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        } else {
            return Integer.parseInt(accountId);
        }
    }
    
    private void redirectToList(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/accounts");
    }
}
