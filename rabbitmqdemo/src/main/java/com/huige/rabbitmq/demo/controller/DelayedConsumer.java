package com.huige.rabbitmq.demo.controller;

import com.huige.rabbitmq.demo.config.DelayedConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class DelayedConsumer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = DelayedConfig.DELAYED_QUEUE)
    public void receive(Message message, Channel channel) throws IOException
    {
        String msg = new String(message.getBody());
        log.info("当前时间： {},收到死信队列信息{}", new Date().toString(), msg);
    }
}
