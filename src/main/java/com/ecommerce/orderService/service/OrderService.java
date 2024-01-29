package com.ecommerce.orderService.service;

import com.ecommerce.orderService.dto.OrderDTO;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO getOrderById(Long id);
}
