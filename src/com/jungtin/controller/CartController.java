package com.jungtin.controller;

import com.jungtin.common.ViewName;
import com.jungtin.model.Cart;
import com.jungtin.model.Product;
import com.jungtin.service.InvoiceService;
import com.jungtin.service.ProductService;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CartController", urlPatterns = "/cart/*")
public class CartController extends HttpServlet {
    private final ProductService productService;
    private final InvoiceService invoiceService;
    
    public CartController() {
        this.productService = new ProductService();
        this.invoiceService = new InvoiceService();
    }
    
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/proceed":
                proceed(request, response);
                break;
        }
    }
    
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getPathInfo();
        if(action == null)
            showCart(request, response);
        else {
            switch (action) {
                case "/add":
                    addToCart(request, response);
                    break;
                case "/remove":
                    removeFromCart(request, response);
                    break;
            }
        }
    }
    
    private void showCart(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(ViewName.CART);
        dispatcher.forward(request, response);
    }
    
    private void addToCart(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Integer id = checkExistIdParam(request, response);
        Product product = productService.findById(id);
        
        HttpSession session = request.getSession();
        Cart sessionCart = (Cart) session.getAttribute("cart");
        if(sessionCart == null) { // Chưa có cart
            Cart cart = new Cart();
            cart.addItem(product);
            session.setAttribute("cart", cart);
        } else { // Đã có cart
            sessionCart.addItem(product);
        }
    
        String redirect_uri = request.getParameter("redirect_uri");
        if(redirect_uri != null) {
            response.sendRedirect(redirect_uri);
            return;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(ViewName.CART);
        dispatcher.forward(request, response);
    }
    
    private void removeFromCart(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Integer id = checkExistIdParam(request, response);
        Product product = productService.findById(id);
        
        HttpSession session = request.getSession();
        Cart sessionCart = (Cart) session.getAttribute("cart");
        if(sessionCart == null) { // Chưa có cart
        
        } else { // Đã có cart
            sessionCart.removeItem(product);
        }
    
        RequestDispatcher dispatcher = request.getRequestDispatcher(ViewName.CART);
        dispatcher.forward(request, response);
    }
    
    private void proceed(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart sessionCart = (Cart) session.getAttribute("cart");
        if(sessionCart == null) { // Chưa có cart
            response.sendRedirect(request.getContextPath() + "/");
        } else { // Đã có cart
            invoiceService.create(request);
            session.removeAttribute("cart");
            RequestDispatcher dispatcher = request.getRequestDispatcher(ViewName.CART);
            dispatcher.forward(request, response);
        }
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
