package com.ecommerce.orderService.exception;

public class BadRequest extends RuntimeException {
    public BadRequest(String errorDescription) {
            super(errorDescription);
        }

}
