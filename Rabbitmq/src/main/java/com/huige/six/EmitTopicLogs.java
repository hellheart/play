package com.huige.six;

import com.huige.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

public class EmitTopicLogs {
    public static final String EXCHANGE_NAME = "topic_log";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtil.getChannel();
        Map<String, String> bindingMap = new HashMap<>();
        bindingMap.put("quick.orange.rabbit","被队列 Q1Q2 接收到");
        bindingMap.put("lazy.orange.elephant","被队列 Q1Q2 接收到");
        bindingMap.put("quick.orange.fox","被队列 Q1 接收到");
        bindingMap.put("lazy.brown.fox","被队列 Q2 接收到");
        bindingMap.put("lazy.pink.rabbit","虽然满足两个绑定但只被队列 Q2 接收一次");
        bindingMap.put("quick.brown.fox","不匹配任何绑定不会被任何队列接收到会被丢弃");
        bindingMap.put("quick.orange.male.rabbit","是四个单词不匹配任何绑定会被丢弃");
        bindingMap.put("lazy.orange.male.rabbit","是四个单词但匹配 Q2");
        for (Map.Entry<String, String> binding : bindingMap.entrySet()) {
            String message = binding.getValue();
            String routingkey = binding.getKey();
            channel.basicPublish(EXCHANGE_NAME,routingkey,null,message.getBytes());
            System.out.println("key 为" + routingkey + " 值为" + message);
        }
    }
}
