package com.alibaba.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @Author shenmeng
 * @Date 2020/7/13
 **/
public class SomeProducerBatch {

    // 第一个泛型：当前生产者所生产消息的key
    // 第二个泛型：当前生产者所生产的消息本身
    private KafkaProducer<Integer, String> producer;

    public SomeProducerBatch() {
        Properties properties = new Properties();
        // 指定kafka集群
        properties.put("bootstrap.servers", "192.168.204.135:9092,192.168.204.136:9092,192.168.204.137:9092");
        // 指定key与value的序列化器
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 指定生产者每10条向broker发送一次
        properties.put("batch.size", 10);
        // 指定生产者每50ms向broker发送一次
        properties.put("linger.ms", 50);
        this.producer = new KafkaProducer<Integer, String>(properties);
    }

    public void sendMsg() {
        for(int i=0; i<50; i++) {
            ProducerRecord<Integer, String> record = new ProducerRecord<>("cities",2,i*10, "city-" + i);
            int k = i;
            producer.send(record, (metadata, ex) -> {
                System.out.println("i = " + k);
                System.out.println("topic = " + metadata.topic());
                System.out.println("partition = " + metadata.partition());
                System.out.println("offset = " + metadata.offset());
                System.out.println("*****************************************");
            });
        }
    }
}
