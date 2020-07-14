package com.alibaba.consumer;

import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * @Author shenmeng
 * @Date 2020/7/13
 **/
public class SomeConsumer extends ShutdownableThread {

    private KafkaConsumer<Integer,String> consumer;

    public SomeConsumer() {
        //name:指定消费者名称
        //isInterruptible：指定消费过程是否会被中断
        super("kafkaConsumerTest", false);
        Properties pro = new Properties();
        //kafka集群 常量可以使用ConsumerConfig中查询
        pro.put("bootstrap.servers","kafkaos1:9092,kafkaos2:9092,kafkaos3:9092");
        //指定消费者组的id
        pro.put("group.id","cityGroup1");
        //开启自动提交，消费完之后，马上提交offset，默认为true
        pro.put("enable.auto.commit","true");
        //设置一次poll从broker读取多少消息
        pro.put("max.poll.records","500");
        //指定自动提交的超时时限，默认为5s
        pro.put("auto.commit.interval.ms","10000");
        //指定消费者被broker认定为挂掉的时间，
        //若broker在此时间内未收到当前消费者发送的心跳，则broker认为消费者已经挂掉了
        //默认为10s
        pro.put("session.timeout.ms","30000");
        //设置心跳周期，即两次心跳的时间间隔，设置不要超过session.timeout的1/3
        pro.put("heartbeat.interval.ms","10000");
        //当kafka中没有指定offset初值时，或指定的offset不存在时，从这里读取offset的值
        //其取值的意义为：
        // earliest:自动重置offset到最早的offset，也就是第一条
        // latest:指定offset为最后一条
        pro.put("auto.offset.reset","earliest");
        //指定key与value的反序列化
        pro.put("key.deserializer","org.apache.kafka.common.serialization.IntegerDeserializer");
        pro.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer=new KafkaConsumer<Integer, String>(pro);

    }

    @Override
    public void doWork() {
        //订阅消息主题
        consumer.subscribe(Collections.singletonList("cities"));
        //从broker拉取消息，参数表示等待消息的时间，若buffer中没有消息，则消费者等待消息的时间
        //0表示如果没有消息，则什么也不返回
        //大于0 表示当时间到后，如果仍然没有消息，则返回空
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        for (ConsumerRecord<Integer, String> record : records) {
            System.out.println("topic = " + record.topic());
            System.out.println("partition = " + record.partition());
            System.out.println("offset = " + record.offset());
            System.out.println("key="+record.key());
            System.out.println("value="+record.value());
        }

    }
}

