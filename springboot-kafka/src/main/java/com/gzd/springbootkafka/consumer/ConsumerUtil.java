package com.gzd.springbootkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * @author gzd
 * @date create in 2019/1/9 17:09
 **/
public class ConsumerUtil extends Thread {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private KafkaConsumer consumer ;

    public ConsumerUtil(){
        this.consumer = createConsumer();
    }

    private KafkaConsumer createConsumer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.247.131:9092,192.168.247.137:9092,192.168.247.138:9092");
        props.put("group.id", "mygorupid");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("max.poll.records", "100");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(props);
        return consumer;
    }

    @Override
    public void run(){

        List<PartitionInfo> list = null;
        list = consumer.partitionsFor("topic");
        if (list != null){
            for (PartitionInfo partitionInfo:list){
                //partitions.add();
            }
        }

        /*consumer.subscribe(Pattern.compile("springbootKafka.*"));*/
        while (true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String,String> record :records ){
                System.out.println("==="+record.topic()+"分区："+record.partition()+"偏移量="+record.offset()+
                        "key值="+record.key()+"value值"+record.value());
                logger.info("-----------------------------------------------");
                logger.info("已消费数据");
            }
            // 异步提交偏移量
            consumer.commitAsync();

        }
    }
    public static void main(String[] args){
        ConsumerUtil consumerUtil = new ConsumerUtil();
        consumerUtil.start();
    }

}
