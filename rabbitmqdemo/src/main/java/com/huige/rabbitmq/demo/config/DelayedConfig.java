package com.huige.rabbitmq.demo.config;

import com.rabbitmq.client.BuiltinExchangeType;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedConfig {

    // 交换机
    public static final String DELAYED_EXCHANGE = "delayed.exchange";

    // 队列
    public static final String DELAYED_QUEUE = "delayed.queue";

    // 路由
    public static final String delayed_routing_key= "delayed.routingkey";


    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message",true, false, arguments);
    }

    @Bean
    public Queue delayedQueue() {
        return QueueBuilder.durable(DELAYED_QUEUE).build();
    }

    @Bean
    public Binding delayedQueueBindingdelayedExchange(@Qualifier("delayedExchange")CustomExchange delayedExchange,
                                                      @Qualifier("delayedQueue")Queue delayedQueue
                                                      ) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(delayed_routing_key).noargs();
    }
}
