package com.gzd.springbootkafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author gzd
 * @date create in 2019/7/7 10:46
 * 自定义 kafka生产者
 **/
public class ProducerConfig {

    public static void main(String[] args){
        Properties kafkaProp = new Properties();
        kafkaProp.put("bootstrap.servers","192.168.247.131:9092,192.168.247.137:9092,192.168.247.138:9092");
        kafkaProp.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        kafkaProp.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer(kafkaProp);

        ProducerRecord record = new ProducerRecord("CustomerContry","Precision Products","France");
        producer.send(record);
        producer.close();

    }
}
