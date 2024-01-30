package com.ecommerce.orderService.exception;

public class OrderRunTimeException extends RuntimeException {

    public OrderRunTimeException(String errorDescription) {
        super(errorDescription);
    }

}
