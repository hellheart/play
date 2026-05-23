package com.huige.seven;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Consumer02 {
    // 死信队列交换机
    public static final String DEAD_EXCHANGE = "dead_exchange";
    // 死信队列
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
        System.out.println("等待接收消息。。。。。。");
        DeliverCallback deliverCallback = (consumeTag, message) -> {
            System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
        };
        channel.basicConsume(DEAD_QUEUE,true,deliverCallback,consumeTag-> {});
    }
}
