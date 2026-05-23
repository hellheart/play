package com.huige.rabbitmq.demo.producter;

import com.huige.rabbitmq.demo.config.ConfirmConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class ConfirmConsumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE)
    public void receive(Message message, Channel channel){
        log.info("收到消息的时间： {}，消息是： {}", new Date().toString(), new String(message.getBody()));
    }

    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE)
    public void receive1(Message message, Channel channel){
        log.info("收到不可用消息，消息是： {}",  new String(message.getBody()));
    }
}
