package com.ecommerce.orderService.service;

import com.ecommerce.orderService.config.KafkaPropertiesReader;
import com.ecommerce.orderService.dto.OrderDTO;
import com.ecommerce.orderService.exception.OrderRunTimeException;
import com.ecommerce.orderService.model.Order;
import com.ecommerce.orderService.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Value("${kafka.new.order.topic}")
    String newOrderTopic;
    @Autowired
    OrderRepository orderRepository;

    Properties properties = KafkaPropertiesReader.read("application.properties");

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        try {
            Order order = new Order();
            BeanUtils.copyProperties(orderDTO, order);

            //get product from inventory and calculate total price
            Double cost = 100.00; //Assumed
            Double totalPrice = orderDTO.getQuantity() * cost;

            //if product is not from inventory throw error

            //deduct the payment from customer account

            Order newOrder = orderRepository.save(order);

            BeanUtils.copyProperties(newOrder, orderDTO);

            orderDTO.setTotalPrice(totalPrice);

            //publish to topic neworders
            KafkaProducer<String, OrderDTO> producer = new KafkaProducer<>(properties);
            ProducerRecord<String, OrderDTO> record = new ProducerRecord<>(newOrderTopic, orderDTO.getCreatedDate(), orderDTO);
            producer.send(record, (metaData, exception) -> {
                if (exception != null) {
                    log.error("exception in sending message-getting metadata", exception);
                    return;
                }
                log.info("record details topic=" + metaData.topic() + " partition-" + metaData.partition() + " offset-" + metaData.offset());

            });

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info("****producer exiting");
            }));


            return orderDTO;
        } catch (Exception e) {
            log.error("Error occurred while creating order - " + e.getMessage());
            throw new OrderRunTimeException("Error occurred while creating order - " + e.getMessage());
        }
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.getReferenceById(id);
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        return orderDTO;
    }

    //kafka-console-consumer.bat --bootstrap-server 192.168.97.11:9092 --property print.key=true --topic total-sale-daywise
    @Bean
    public Function<KStream<String, OrderDTO>, KStream<String, String>> totalSale() {
            return inputStream -> inputStream
                    .peek((createdDate, orderDTO) -> System.out.println("messages coming in key=" + createdDate + "value=" + orderDTO))
                    .groupByKey()
                    .aggregate(() -> 0.0, (key,value,aggregate) -> aggregate + value.getTotalPrice(), Materialized.with(Serdes.String(),Serdes.Double()))
                    .toStream()
                    .map((w,v) -> new KeyValue<>(w, v.toString())).peek((w,v) -> System.out.println("total sale key ="+w+" ,value= "+v.toString()));
    }

}
