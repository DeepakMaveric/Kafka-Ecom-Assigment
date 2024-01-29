package com.ecommerce.orderService.config;

import com.ecommerce.orderService.dto.OrderDTO;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class OrderSerde implements Serde<OrderDTO> {
    @Override
    public Serializer<OrderDTO> serializer() {
        return new OrderSerializer();
    }

    @Override
    public Deserializer<OrderDTO> deserializer() {
        return new OrderDeserializer();
    }
}
