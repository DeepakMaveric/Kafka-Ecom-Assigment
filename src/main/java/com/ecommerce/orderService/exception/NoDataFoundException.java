package com.ecommerce.orderService.exception;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String errorDescription) {
        super(errorDescription);
    }
}
