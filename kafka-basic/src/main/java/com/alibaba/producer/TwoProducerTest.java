package com.alibaba.producer;

import java.io.IOException;

/**
 * @Author shenmeng
 * @Date 2020/7/13
 **/
public class TwoProducerTest {

    public static void main(String[] args) throws IOException {
        TwoProducer twoProducer = new TwoProducer();
        twoProducer.sendMsg();
        System.in.read();
    }
}
