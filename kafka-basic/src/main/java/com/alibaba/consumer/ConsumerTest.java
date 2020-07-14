package com.alibaba.consumer;

import java.io.IOException;

public class ConsumerTest {
    public static void main(String[] args) throws IOException {
        SomeConsumer consumer = new SomeConsumer();
        consumer.start();
        System.in.read();
    }
}


