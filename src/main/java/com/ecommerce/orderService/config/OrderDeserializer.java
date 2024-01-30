package com.ecommerce.orderService.config;

import com.ecommerce.orderService.dto.OrderDTO;
import com.ecommerce.orderService.exception.OrderRunTimeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class OrderDeserializer implements Deserializer<OrderDTO> {
  private static final ObjectMapper mapper=new ObjectMapper();
    @Override
    public OrderDTO deserialize(String topic, byte[] data) {
        try {
            OrderDTO order = mapper.readValue(data, OrderDTO.class);
            return order;
        }
        catch (Exception e){
            throw new OrderRunTimeException("Error occurred while deserialization"+e.getMessage());
        }
    }
}
