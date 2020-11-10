package com.jungtin.controller;

import com.jungtin.common.ViewName;
import com.jungtin.common.Validator;
import com.jungtin.model.Product;
import com.jungtin.service.ProductService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductController", urlPatterns = {
    "/products/*"
})
public class ProductController extends HttpServlet {
    
    private final ProductService productService;
    private final Validator validator;
    
    public ProductController() {
        this.productService = new ProductService();
        this.validator = new Validator();
    }
    
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/process":
                processForm(request, response);
                break;
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
        RequestDispatcher dispatcher = request.getRequestDispatcher(ViewName.PRODUCT_FORM);
        dispatcher.forward(request, response);
    }
    
    /*
    *   UPDATE
    * */
    private void showUpdateForm(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Integer id  = checkExistIdParam(request, response);
        Product product = productService.findById(id);
        request.setAttribute("product", product);
        request.getRequestDispatcher(ViewName.PRODUCT_FORM).forward(request, response);
    }
    
    private void processForm(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HashMap<String, String> errors = new HashMap<>();
        
        validator.checkRequired("name", request, errors);
        validator.checkRequired("image_url", request, errors);
        validator.checkRequired("price", request, errors);
        validator.checkRequired("quantity", request, errors);
    
        validator.checkDouble("price", request, errors);
        validator.checkInteger("quantity", request, errors);
        validator.checkUrl("image_url", request, errors);
        
        if(errors.size() > 0) {
            HashMap<String, String> product = new HashMap<>();
            product.put("name", request.getParameter("name"));
            product.put("imageUrl", request.getParameter("image_url"));
            product.put("price", request.getParameter("price"));
            product.put("quantity", request.getParameter("quantity"));
            
            request.setAttribute("product", product);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher(ViewName.PRODUCT_FORM).forward(request, response);
            return;
        }
        
        productService.saveOrUpdate(request);
        redirectToList(request, response);
    }
    /*
    *   DELETE
    * */
    private void delete(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Integer id = checkExistIdParam(request, response);
        boolean delete = productService.delete(id);
        if(!delete) {
            request.setAttribute("error", "Can't delete product");
            getAll(request, response);
            return;
        }
        
        redirectToList(request, response);
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
        request.getRequestDispatcher(ViewName.PRODUCT_LIST)
            .forward(request, response);
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
    
    private void redirectToList(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/products");
    }
}
