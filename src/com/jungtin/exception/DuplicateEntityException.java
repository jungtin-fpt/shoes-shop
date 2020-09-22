package com.jungtin.exception;

public class DuplicateEntityException extends RuntimeException {
    
    public DuplicateEntityException() {
        super();
    }
    
    public DuplicateEntityException(String message) {
        super(message);
    }
}
