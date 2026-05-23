package com.huige.three;

import com.huige.util.RabbitMqUtil;
import com.huige.util.SleepUtil;
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
            SleepUtil.sleep(1);
            System.out.println("C1接收的消息:"+ new String(message.getBody()));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        /**
         * 开启不公平分发
         */
        int prefetchCount = 2;
        channel.basicQos(prefetchCount);
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck, deliverCallback, (ca) -> {});
    }
}
