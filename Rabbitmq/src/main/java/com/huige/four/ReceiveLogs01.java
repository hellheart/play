package com.huige.four;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.nio.charset.StandardCharsets;

public class ReceiveLogs01 {

    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args)  throws Exception{
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
            System.out.println("C1消费者接收的消息");
        };
        channel.basicConsume(queueName, true,deliverCallback , s -> {});
    }
}
