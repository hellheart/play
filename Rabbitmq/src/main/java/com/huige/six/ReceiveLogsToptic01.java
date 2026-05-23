package com.huige.six;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class ReceiveLogsToptic01 {
    public static final String EXCHANGE_NAME = "topic_log";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = "Q1";
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName, EXCHANGE_NAME,"*.orange.*");
        System.out.println("C1准备接收消息");
        DeliverCallback deliverCallback = (tag , message) -> {
            System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
        };
        channel.basicConsume(queueName,true,deliverCallback, s-> {});
    }

}
