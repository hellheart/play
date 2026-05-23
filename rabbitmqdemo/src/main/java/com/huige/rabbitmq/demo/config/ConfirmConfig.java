package com.huige.rabbitmq.demo.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConfirmConfig {

    public static final String CONFIRM_EXCHANGE = "confirm_exchange";
    public static final String BACKUP_EXCHANGE = "backup_exchange";



    public static final String CONFIRM_QUEUE = "confirm_queue";
    public static final String BACKUP_QUEUE = "backup_queue";
    public static final String WARNING_QUEUE = "warning_queue";

    public static final String ROUTING_KEY = "key1";

    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("alternate-exchange", BACKUP_EXCHANGE);
        return new DirectExchange(CONFIRM_EXCHANGE,true,false,arguments);
    }

    @Bean("backup_exchange")
    public FanoutExchange backupExchange() {

        return new FanoutExchange(BACKUP_EXCHANGE);
    }

    @Bean("confirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean
    public Queue backupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE).build();
    }
    @Bean
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE).build();
    }

    @Bean
    public Binding cqBindingcE(@Qualifier("confirmExchange") DirectExchange confirmExchange,
                               @Qualifier("confirmQueue") Queue confirmQueue) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding cqBindingca(@Qualifier("backup_exchange") FanoutExchange backupExchange,
                               @Qualifier("backupQueue") Queue backupQueue) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    @Bean
    public Binding cqBindingcb(@Qualifier("backup_exchange") FanoutExchange backupExchange,
                               @Qualifier("warningQueue") Queue warningQueue) {
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
}
