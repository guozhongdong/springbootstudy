package com.gzd.springbootkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author gzd
 * @date create in 2019/1/9 16:24
 **/
@Component
public class Consumer {

    @KafkaListener(topics = {"springbootKafka"})
    public void consumer(ConsumerRecord<?, ?> record){
        System.out.println("topic消息"+record.partition()+"--------------"+record.value());
    }
}
