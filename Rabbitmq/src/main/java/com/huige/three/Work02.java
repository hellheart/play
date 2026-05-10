package com.huige.three;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Work01 {
    private static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtil.getChannel();
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            System.out.println("C1接收的消息:"+ new String(message.getBody()));
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck, deliverCallback, (ca) -> {});
    }
}
