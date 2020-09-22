package com.jungtin.security;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SecurityConfig {
    public static final List<String> protectUrls = new ArrayList<>();
    
    static {
        init();
    }
    
    private static void init() {
        protectUrls.add("/accounts");
        protectUrls.add("/products");
        protectUrls.add("/invoices");
    }
}