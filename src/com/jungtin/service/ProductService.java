package com.jungtin.service;

import com.jungtin.exception.DuplicateEntityException;
import com.jungtin.exception.EntityNotFoundException;
import com.jungtin.model.Product;
import com.jungtin.repository.ProductRepository;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ProductService {
    private final ProductRepository productRepository;
    
    public ProductService() {
        this.productRepository = new ProductRepository();
    }
    
    public void saveOrUpdate(HttpServletRequest request) {
        String name = request.getParameter("name");
        
        Product product = new Product();
        product.setName(name);
        product.setImageUrl(request.getParameter("image_url"));
        product.setPrice(Double.parseDouble(request.getParameter("price")));
        product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        product.setLastImportDate(LocalDate.now());
        
        if(request.getParameter("id") == null || request.getParameter("id").isBlank()) {
            // Create
            boolean existProduct = productRepository.checkExistProduct(name);
            if(existProduct)
                throw new DuplicateEntityException(String.format("Duplicated : Product [name : %s] already existed", name));
            
            productRepository.createProduct(product);
        } else {
            // Update
            Integer id = Integer.parseInt(request.getParameter("id"));
            product.setId(id);
            productRepository.updateProduct(product);
        }
        
    }
    
    public List<Product> findAll() {
        return productRepository.getAllProduct();
    }

    public Product findById(int id) {
        boolean exist = productRepository.checkExistProduct_withId(id);
        if (!exist)
            throw new EntityNotFoundException(
                String.format("Product [id : %s] isn't exists", id));
        
        return productRepository.getProduct(id);
    }

    public boolean delete(int id) {
        return productRepository.deleteProduct(id);
    }
}
