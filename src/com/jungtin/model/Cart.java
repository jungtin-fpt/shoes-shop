package com.jungtin.model;

import java.util.HashMap;
import lombok.Data;

@Data
public class Cart {
    private HashMap<Product, Integer> items = new HashMap<>();
    
    public void addItem(Product product) {
        if(items.containsKey(product)) {
            Integer quantity = items.get(product);
            items.put(product, ++quantity);
        } else {
            items.put(product, 1);
        }
    }
    
    public void removeItem(Product product) {
        items.remove(product);
    }
}
