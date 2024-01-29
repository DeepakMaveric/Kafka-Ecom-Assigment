package com.ecommerce.orderService.config;

import com.ecommerce.orderService.dto.OrderDTO;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class OrderSerializer implements Serializer<OrderDTO> {
    @Override
    public byte[] serialize(String topic, OrderDTO data) {
        String text="orderId - "+data.getId()
                +", productId - "+data.getProductId()
                +", quantity - "+data.getQuantity()
                +", totalPrice - "+data.getTotalPrice()
                +", status - "+data.getStatus()
                +", createdDate - "+data.getCreatedDate();
        return text.getBytes(StandardCharsets.UTF_8);
    }
}
