package com.jungtin.controller;

import com.jungtin.common.ViewName;
import com.jungtin.model.Product;
import com.jungtin.service.ProductService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HomeController", urlPatterns = "/")
public class HomeController extends HttpServlet {
    private final ProductService productService;
    
    public HomeController() {
        this.productService = new ProductService();
    }
    
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
    
        list(request, response);
    }
    
    private void list(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String productId = request.getParameter("id");
        if (productId != null) {
            getOne(request, response);
        } else
            getAll(request, response);
    }
    
    private void getOne(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Integer id = checkExistIdParam(request, response);
        Product product = productService.findById(id);
        request.setAttribute("product", product);
        request.getRequestDispatcher(ViewName.PRODUCT_DETAIL)
            .forward(request, response);
    }
    
    private void getAll(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        List<Product> products = productService.findAll();
        request.setAttribute("products", products);
    
        request.getRequestDispatcher(ViewName.INDEX).forward(request, response);
    }
    
    /*
     *  Tools
     * */
    private Integer checkExistIdParam(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String productId = request.getParameter("id");
        if (productId == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        } else {
            return Integer.parseInt(productId);
        }
    }
}
