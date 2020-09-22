package com.jungtin.service;

import com.jungtin.exception.EntityNotFoundException;
import com.jungtin.model.Cart;
import com.jungtin.model.Invoice;
import com.jungtin.model.Product;
import com.jungtin.repository.InvoiceRepository;
import com.jungtin.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    
    public InvoiceService() {
        this.invoiceRepository = new InvoiceRepository();
        this.productRepository = new ProductRepository();
    }
    
    public void create(HttpServletRequest request) {
        Invoice invoice = new Invoice();
        invoice.setCustName(request.getParameter("cust_name"));
        invoice.setCustAddress(request.getParameter("cust_addr"));
        invoice.setCustPhoneNo(request.getParameter("cust_phone_no"));
        invoice.setCreateDateTime(LocalDateTime.now());
        
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        invoice.setProducts(cart.getItems());
        
        if(request.getParameter("id") == null || request.getParameter("id").isBlank()) {
            // Create
            invoiceRepository.createInvoice(invoice);
            for (Map.Entry<Product, Integer> entry : invoice.getProducts().entrySet()) {
                Product product = entry.getKey();
                Integer buyQuantity = entry.getValue();
                productRepository.updateQuantity(product.getId(), buyQuantity);
            }
        } else {
            // Update
            Integer id = Integer.parseInt(request.getParameter("id"));
            invoice.setId(id);
            System.out.println("Is trying to update invoice! But currently not supported");
//            invoiceRepository.updateInvoice(invoice);
        }
        
    }
    
    public List<Invoice> findAll() {
        return invoiceRepository.getAllInvoice();
    }

    public Invoice findById(int id) {
        boolean exist = invoiceRepository.checkExistInvoice_withId(id);
        if (!exist)
            throw new EntityNotFoundException(
                String.format("Invoice [id : %s] isn't exists", id));
        
        return invoiceRepository.getInvoice(id);
    }

//    public void delete(int id) {
//        invoiceRepository.deleteInvoice(id);
//    }

//    public boolean isExist(String name) {
//        return invoiceRepository.checkExistInvoice(name);
//    }
}
