package com.jungtin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Account {
    
    private int id;
    
    private String username;
    
    private String password;
    
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
