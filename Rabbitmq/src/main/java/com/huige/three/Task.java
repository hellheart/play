package com.huige.three;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Task {
    private static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtil.getChannel();
        boolean druable = true; // 消息队列持久化
        channel.queueDeclare(QUEUE_NAME,druable,false,false,null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes("UTF-8"));
            System.out.println("消息已经发送" );
        }
    }
}
