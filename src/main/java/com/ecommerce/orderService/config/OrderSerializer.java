package com.ecommerce.orderService.config;

import com.ecommerce.orderService.dto.OrderDTO;
import com.ecommerce.orderService.exception.OrderRunTimeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class OrderSerializer implements Serializer<OrderDTO> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, OrderDTO data) {
        try {
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
           throw new OrderRunTimeException("Error while serializing - " + e.getMessage());
        }
    }
}
