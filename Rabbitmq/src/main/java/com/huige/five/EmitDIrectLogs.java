package com.huige.five;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

public class EmitDIrectLogs {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtil.getChannel();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes());
            System.out.println("生产者发布消息：" + message);
        }
    }
}
