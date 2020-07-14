package com.alibaba.producer;

/**
 * @Author shenmeng
 * @Date 2020/7/13
 **/
public class OneProducerTest {

    public static void main(String[] args) {
        OneProducer oneProducer = new OneProducer();
        oneProducer.sendMsg();
    }
}
