package com.huige.rabbitmq.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData.getId() != null ? correlationData.getId() : "";
        if (ack) {
           log.info("交换机已经收到id为： {}的消息",id);
        } else {
            log.info("交换机还未收到id为{}的消息，原因是：{}",id,cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        int replyCode = returned.getReplyCode();
        Message message = returned.getMessage();
        String replyText = returned.getReplyText();
        String exchange = returned.getExchange();
        log.info("消息{}，交换机{}，退回原因：{}，响应码：{}",message,exchange,replyText,replyCode);
    }
}
