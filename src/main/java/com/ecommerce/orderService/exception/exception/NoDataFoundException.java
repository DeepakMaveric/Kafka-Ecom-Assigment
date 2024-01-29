package com.ecommerce.orderService.exception.exception;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String errorDescription) {
        super(errorDescription);
    }
}
