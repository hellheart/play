package com.huige.seven;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Consumer01 {
    // 死信队列交换机
    public static final String DEAD_EXCHANGE = "dead_exchange";
    // 普通队列交换机
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    // 死信队列
    public static final String DEAD_QUEUE = "dead_queue";
    // 普通队列
    public static final String NORMAL_QUEUE = "normal_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "lisi");
        // 设置队列最大长度
        // arguments.put("x-max-length", 4);
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,arguments);
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhansan");

        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
        System.out.println("等待接收消息。。。。。。");
        DeliverCallback deliverCallback = (consumeTag, message) -> {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            // 消息拒绝
            if(msg.equals("消息1")) {
                long deliveryTag = message.getEnvelope().getDeliveryTag();
                channel.basicReject(deliveryTag, false);
            } else {
                System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback,consumeTag-> {});
    }
}
