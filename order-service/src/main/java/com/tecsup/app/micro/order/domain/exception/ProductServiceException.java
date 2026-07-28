package com.tecsup.app.micro.order.domain.exception;

public class ProductServiceException extends RuntimeException {
    public ProductServiceException(String message) {
        super(message);
    }
    public ProductServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

