package com.gzd.springbootkafka.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * @author gzd
 * @date create in 2019/7/7 11:57
 * 消费者群组
 **/
public class ConsumerConfig {


    public static void main(String[] args){

        Properties kafkaProp = new Properties();

        kafkaProp.put("bootstrap.servers","192.168.247.131:9092,192.168.247.137:9092,192.168.247.138:9092");
        kafkaProp.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProp.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProp.put("group.id","country");
        ConsumerThread consumerThread = new ConsumerThread(new KafkaConsumer<String,String>(kafkaProp));
        consumerThread.start();
    }

}
