package com.ecommerce.orderService.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ErrorDTO implements Serializable {

  private String errorCode;
  private String errorDescription;

}
