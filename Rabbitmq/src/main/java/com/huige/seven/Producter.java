package com.huige.seven;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class Producter {
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static final String NORMAL_QUEUE = "normal_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtil.getChannel();
        // 过期时间设置
        //AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 1; i < 11; i++) {
            String message = "消息" + i;
            channel.basicPublish(NORMAL_EXCHANGE,"zhansan",null,message.getBytes());
            System.out.println("生产者发送消息" + message);
        }
    }
}
