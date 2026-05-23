package com.huige.four;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

public class EmitLog {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws  Exception{
        Channel channel = RabbitMqUtil.getChannel();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("生产者发送消息" + message);
        }
    }
}
