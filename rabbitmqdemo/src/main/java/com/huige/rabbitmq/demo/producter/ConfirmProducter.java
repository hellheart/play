package com.huige.rabbitmq.demo.producter;


import com.huige.rabbitmq.demo.config.ConfirmConfig;
import com.huige.rabbitmq.demo.config.MyCallBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequestMapping("/confirm")
public class ConfirmProducter implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback{

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(this);
    }

    @GetMapping("/sendMsg/{message}")
    public void sendMessage(@PathVariable("message") String message) {
        CorrelationData correlationData = new CorrelationData("1");
        String routingkey1 = "key1";
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE, routingkey1, message+ routingkey1, correlationData);
        CorrelationData correlationData1 = new CorrelationData("2");
        routingkey1 = "key2";
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE, routingkey1, message+ routingkey1, correlationData1);
        log.info("发送消息内容： {}", message);
    }

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
