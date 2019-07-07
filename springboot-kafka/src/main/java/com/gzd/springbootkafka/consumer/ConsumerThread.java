package com.gzd.springbootkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Optional;

/**
 * @author gzd
 * @date create in 2019/7/7 14:44
 **/
public class ConsumerThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerThread.class);

    private KafkaConsumer consumer;

    public ConsumerThread(KafkaConsumer consumer ){
        this.consumer = consumer;
    }

    @Override
    public void run() {
        consumer.subscribe(Collections.singletonList("CustomerContry"));
        try {
            while (true){
                ConsumerRecords<String,String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    Optional<?> kafkaMessage = Optional.ofNullable(record.value());
                    if (kafkaMessage.isPresent()) {
                        LOGGER.info("-------------[本次消费者线程：" + record.partition() + " 消费Topic：" + record.topic() +
                                "] record = " + record);
                    }

                }
                consumer.commitAsync();

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
