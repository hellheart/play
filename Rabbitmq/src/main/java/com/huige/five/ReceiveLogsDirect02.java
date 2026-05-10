package com.huige.five;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;


import java.nio.charset.StandardCharsets;

public class ReceiveLogsDirect01 {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare("disk",false,false,false,null);
        channel.queueBind("disk", EXCHANGE_NAME, "error");
        System.out.println("转杯接收消息");
        DeliverCallback deliverCallback = (tag , message) -> {
            System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
        };
        channel.basicConsume("disk",true, deliverCallback, s -> {});
    }
}
