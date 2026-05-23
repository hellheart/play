package com.huige.five;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class ReceiveLogsDirect02 {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare("console",false,false,false,null);
        channel.queueBind("console", EXCHANGE_NAME, "info");
        channel.queueBind("console", EXCHANGE_NAME, "warning");
        System.out.println("C2转杯接收消息");
        DeliverCallback deliverCallback = (tag , message) -> {
            System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
        };
        channel.basicConsume("console",true, deliverCallback, s -> {});
    }
}
