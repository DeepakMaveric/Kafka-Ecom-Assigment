package com.ecommerce.orderService.exception.exception;

public class BadRequest extends RuntimeException {
    public BadRequest(String errorDescription) {
            super(errorDescription);
        }

}
