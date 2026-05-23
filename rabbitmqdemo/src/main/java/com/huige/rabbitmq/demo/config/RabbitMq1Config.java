package com.huige.rabbitmq.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMq1Config {
    // 普通交换机
    public static final String X_EXCHANGE = "x_exchange";
    // 死信队列交换机
    public static final String Y_DEAD_LEETER_EXCHANGE = "y_exchange";
    // 普通队列
    public static final String a_queue = "QA";
    public static final String b_queue = "QB";

    // 死信队列
    public static final String DEAD_LETTER_QUEUE = "QD";

    // 普通队列
    public static final String c_queue = "QC";

    @Bean("QC")
    public Queue queueC() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", Y_DEAD_LEETER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "YD");
        return QueueBuilder.durable(c_queue).withArguments(arguments).build();
    }

    @Bean("xExchange")
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }
    @Bean("yExchange")
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LEETER_EXCHANGE);
    }

    @Bean("QA")
    public Queue aqueue() {
        Map<String, Object> arguments = new HashMap<>(3);
        arguments.put("x-dead-letter-exchange", Y_DEAD_LEETER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "YD");
        arguments.put("x-message-ttl", 10000);
        return QueueBuilder.durable(a_queue).withArguments(arguments).build();
    }

    @Bean("QB")
    public Queue bqueue() {
        Map<String, Object> arguments = new HashMap<>(3);
        arguments.put("x-dead-letter-exchange", Y_DEAD_LEETER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "YD");
        arguments.put("x-message-ttl", 40000);
        return QueueBuilder.durable(b_queue).withArguments(arguments).build();
    }

    @Bean("QD")
    public Queue dqueue() {
        return new Queue(DEAD_LETTER_QUEUE);
    }

    @Bean
    public Binding xbindinga(@Qualifier("xExchange") DirectExchange xExchange,
                             @Qualifier("QA") Queue aqueue
    ) {
        return BindingBuilder.bind(aqueue).to(xExchange).with("XA");
    }

    @Bean
    public Binding xbindingb(@Qualifier("xExchange") DirectExchange xExchange,
                             @Qualifier("QB") Queue bqueue
    ) {
        return BindingBuilder.bind(bqueue).to(xExchange).with("XB");
    }

    @Bean
    public Binding ybindingd(@Qualifier("yExchange") DirectExchange xExchange,
                             @Qualifier("QD") Queue dqueue) {
        return BindingBuilder.bind(dqueue).to(xExchange).with("YD");
    }

    @Bean
    public Binding queueCBindingxExchange(@Qualifier("xExchange") DirectExchange xExchange,
                                          @Qualifier("QC") Queue queueC
                                          ) {
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }
}
