package com.gzd.springbootkafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @author gzd
 * @date create in 2019/1/9 16:15
 **/
@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("spring.kafka.bootstrap-servers")
    private String servers;



}
