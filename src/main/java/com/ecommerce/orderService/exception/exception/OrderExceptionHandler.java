package com.ecommerce.orderService.exception.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
@Slf4j
public class OrderExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OrderRunTimeException.class)
    public ResponseEntity<ErrorDTO> assertionException(OrderRunTimeException e) {
        ErrorDTO error = new ErrorDTO();
        error.setErrorDescription(e.getMessage());
        error.setErrorCode("500");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NoDataFoundException.class})
    public ResponseEntity<ErrorDTO> resourceNotFoundExceptionHandler(NoDataFoundException e) {
        ErrorDTO error = new ErrorDTO();
        error.setErrorDescription(e.getMessage());
        error.setErrorCode("404");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequest.class})
    public ResponseEntity<ErrorDTO> badRequestExceptionHandler(BadRequest e) {
        ErrorDTO error = new ErrorDTO();
        error.setErrorDescription(e.getMessage());
        error.setErrorCode("402");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<ErrorDTO> handleSqlExceptionExceptionHandler(SQLIntegrityConstraintViolationException e) {
        ErrorDTO error = new ErrorDTO();
        error.setErrorDescription(e.getMessage());
        error.setErrorCode("402");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders
            headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        ErrorDTO err = new ErrorDTO();
        err.setErrorDescription(errors.toString());
        err.setErrorCode("402");
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
