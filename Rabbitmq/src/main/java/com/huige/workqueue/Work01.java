package com.huige.workqueue;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Work01 {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtil.getChannel();
        DeliverCallback deliverCallback = (a, b) ->{
            System.out.println(new String(b.getBody()));
        };
        CancelCallback cancelCallback = (s) -> {
            System.out.println("消费失败的回调");
        };
        System.out.println("C2消费");
        channel.basicConsume(QUEUE_NAME,true, deliverCallback,cancelCallback);
    }
}
