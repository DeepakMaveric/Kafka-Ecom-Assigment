package com.ecommerce.orderService.service;

import com.ecommerce.orderService.dto.OrderDTO;
import com.ecommerce.orderService.model.Order;
import com.ecommerce.orderService.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        try {
            Order order = new Order();
            BeanUtils.copyProperties(orderDTO, order);

            //get product from inventory and calculate total price
            //if product is not from inventory throw error

            //deduct the payment from customer account

            Order newOrder = orderRepository.save(order);

            //publish to topic neworders

            BeanUtils.copyProperties(newOrder, orderDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDTO;
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.getReferenceById(id);
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        return orderDTO;
    }
}
