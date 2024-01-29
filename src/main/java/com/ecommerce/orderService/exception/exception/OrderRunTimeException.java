package com.ecommerce.orderService.exception.exception;

public class OrderRunTimeException extends RuntimeException {

    public OrderRunTimeException(String errorDescription) {
        super(errorDescription);
    }

}
