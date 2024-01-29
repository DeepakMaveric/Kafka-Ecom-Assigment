package com.ecommerce.orderService.config;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Properties;

public class KafkaPropertiesReader {

    @SneakyThrows
    public  static Properties read(String fileName){
     InputStream inputStream= KafkaPropertiesReader.class.getClassLoader().
                getResourceAsStream(fileName);
     Properties properties=new Properties();
     properties.load(inputStream);
     return properties;
    }

}
