package com.gzd.springbootkafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author gzd
 * @date create in 2019/1/9 15:20
 * kafka生产者
 **/
@Component
public class Producer {

    @Autowired
    private KafkaTemplate template;

    public void send(){
        template.send("springbootKafka4","message!====");
    }
}
