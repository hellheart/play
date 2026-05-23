package com.huige.rabbitmq.demo.controller;

import com.huige.rabbitmq.demo.config.DelayedConfig;
import com.huige.rabbitmq.demo.config.RabbitMq1Config;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/ttl")
public class ConsumerController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendMsg(HttpServletRequest req, HttpServletResponse resp,@PathVariable("message") String message) {
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
            String[] value = stringEntry.getValue();
        }
        log.info("发送消息的时间：{}，发送一条消息给TTL队列： {}",new Date().toString(),message);
        rabbitTemplate.convertAndSend("x_exchange", "XA", "消息来自 ttl 为 10S 的队列: "+message);
        rabbitTemplate.convertAndSend("x_exchange", "XB", "消息来自 ttl 为 40S 的队列: "+message);
    }

    @GetMapping("/sendMsg/{message}/{ttl}")
    public void sendMsg(@PathVariable("message") String message,
                        @PathVariable("ttl") String ttl) {

        log.info("发送消息的时间：{}，发送一条{}毫秒消息给TTL队列： {}",new Date().toString(),ttl,message);
        rabbitTemplate.convertAndSend(RabbitMq1Config.X_EXCHANGE,"XC", message, msg -> {
            msg.getMessageProperties().setExpiration(ttl);
            return msg;
        });
    }

    @GetMapping("/sendDelayedMsg/{message}/{delayedTime}")
    public void sendMsg(@PathVariable("message") String message,
                        @PathVariable("delayedTime") Integer delayedTime) {

        log.info("发送消息的时间：{}，发送一条{}毫秒消息给TTL队列： {}",new Date().toString(),delayedTime,message);
        rabbitTemplate.convertAndSend(DelayedConfig.DELAYED_EXCHANGE,DelayedConfig.delayed_routing_key, message, msg -> {
            msg.getMessageProperties().setDelay(delayedTime);
            return msg;
        });
    }
}
