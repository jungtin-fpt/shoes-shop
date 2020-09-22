package com.jungtin.model;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of = "id")
//...
@NoArgsConstructor
@Data
public class Product {
    private int id;
    private String name;
    private String imageUrl;
    private Double price;
    private int quantity;
    private LocalDate lastImportDate;
    
    public Product(String name, String imageUrl, Double price, int quantity,
        LocalDate lastImportDate) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
        this.lastImportDate = lastImportDate;
    }
}
