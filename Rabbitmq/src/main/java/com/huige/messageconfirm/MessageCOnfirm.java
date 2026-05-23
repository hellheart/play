package com.huige.messageconfirm;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class MessageCOnfirm {
    /**
     * 消息发送总数
     */
    public static final Integer MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception {
        /**
         * 单条消息确认
         */
        //messageByOne(); //单个消息确认花费的时间39286ms.
        /**
         * 批量确认
         */
        // messageBatch(); // 批量消息确认花费的时间137ms.
        /**
         * 异步确认
         */
        messageAsync(); // 异步消息确认花费的时间82ms.
    }

    public static void messageByOne() throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.confirmSelect();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false, false, null);
        long begin = System.currentTimeMillis();
        for (Integer i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            channel.waitForConfirms();
        }
        long end = System.currentTimeMillis();
        System.out.println("单个消息确认花费的时间" + (end - begin) + "ms.");
    }

    public static void messageBatch() throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.confirmSelect();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false, false, null);
        long begin = System.currentTimeMillis();
        //批量确认消息大小
        int batchSize = 100;
//未确认消息个数
        int outstandingMessageCount = 0;
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            outstandingMessageCount++;
            if (outstandingMessageCount == batchSize) {
                channel.waitForConfirms();
                outstandingMessageCount = 0;
            }
        }
//为了确保还有剩余没有确认消息 再次确认
        if (outstandingMessageCount > 0) {
            channel.waitForConfirms();
        }
        long end = System.currentTimeMillis();
        System.out.println("批量消息确认花费的时间" + (end - begin) + "ms.");
    }

    public static void messageAsync() throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false, false, null);
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        ConfirmCallback ackCallback = (deliveryTag, multiple) -> {
            System.out.println("消息发送成功的回调" + deliveryTag);
        };
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            System.out.println("消息发送失败的回调" + deliveryTag);
        };
        channel.addConfirmListener(ackCallback, nackCallback);
        for (Integer i = 0; i < MESSAGE_COUNT; i++) {
            String message = "" + i;
            channel.basicPublish("", queueName, null, message.getBytes());
        }
        long end = System.currentTimeMillis();
        System.out.println("异步消息确认花费的时间" + (end - begin) + "ms.");
    }
}
