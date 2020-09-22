package com.jungtin.controller;

import com.jungtin.common.ViewName;
import com.jungtin.model.Invoice;
import com.jungtin.service.InvoiceService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "InvoiceController", urlPatterns = {
    "/invoices/*"
})
public class InvoiceController extends HttpServlet {
    
    private final InvoiceService invoiceService;
    
    public InvoiceController() {
        this.invoiceService = new InvoiceService();
    }
    
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
//        String action = request.getPathInfo();
//        if(action == null)
            list(request, response);
//        else {
//            switch (action) {
//                case "/delete":
//                    delete(request, response);
//                    break;
//            }
//        }
    }
    
    /*
    *   DELETE
    * */
//    private void delete(
//        HttpServletRequest request, HttpServletResponse response)
//        throws ServletException, IOException {
//        Integer id = checkExistIdParam(request, response);
//        invoiceService.delete(id);
//        redirectToList(request, response);
//    }

    private void list(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String invoiceId = request.getParameter("id");
        if (invoiceId != null) {
            getOne(request, response);
        } else
            getAll(request, response);
    }

    private void getOne(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Integer id = checkExistIdParam(request, response);
        Invoice invoice = invoiceService.findById(id);
        request.setAttribute("invoice", invoice);
        request.getRequestDispatcher(ViewName.INVOICE_DETAIL)
            .forward(request, response);
    }

    private void getAll(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        List<Invoice> invoices = invoiceService.findAll();
        request.setAttribute("invoices", invoices);
        request.getRequestDispatcher(ViewName.INVOICE_LIST)
            .forward(request, response);
    }
    
    /*
    *  Tools
    * */
    private Integer checkExistIdParam(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String invoiceId = request.getParameter("id");
        if (invoiceId == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        } else {
            return Integer.parseInt(invoiceId);
        }
    }
    
    private void redirectToList(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/invoices");
    }
}
