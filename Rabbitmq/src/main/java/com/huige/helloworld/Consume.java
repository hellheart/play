package com.huige.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Consume {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPassword("admin");
        connectionFactory.setUsername("admin");
        connectionFactory.setHost("150.158.21.175");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(consumerTag);
            System.out.println(new String(message.getBody()));
        };
        CancelCallback cancelCallback = (s) -> {
            System.out.println("中断了");
        };
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority", 10);
        channel.queueDeclare(QUEUE_NAME,true,false,false, arguments);
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback);
    }
}
