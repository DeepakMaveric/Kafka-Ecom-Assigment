package com.ecommerce.orderService.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    @NotEmpty(message = "productId required")
    private String productId;
    @Min(0)
    private Integer quantity;
    private String status = "not dispatched";
    private Double totalPrice;
    private Date createdDate;
}
