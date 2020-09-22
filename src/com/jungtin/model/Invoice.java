package com.jungtin.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.Data;

@Data
public class Invoice {
    private int id;
    private String custName;
    private String custAddress;
    private String custPhoneNo;
    private LocalDateTime createDateTime;
    private HashMap<Product, Integer> products = new HashMap<>();
}
