package com.jackmanwu.rocketmq.demo.config;

import com.jackmanwu.rocketmq.demo.config.properties.RocketMQProperties;
import com.jackmanwu.rocketmq.demo.enums.TaskEnum;
import com.jackmanwu.rocketmq.demo.listener.OrderListener;
import com.jackmanwu.rocketmq.demo.listener.ProductListener;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author jackmanwu
 * @create 2019-09-24 14:19:27
 * @description
 */
@Log4j2
@Configuration
@EnableConfigurationProperties(RocketMQProperties.class)
public class RocketMQConsumerConfig {
    @Autowired
    private RocketMQProperties properties;

    @Autowired
    private OrderListener orderListener;

    @Autowired
    private ProductListener productListener;

    /**
     * 订单消费
     */
    @PostConstruct
    public void orderConsume() {
        log.info("初始化订单消费");
        consume(properties.getMap().get(TaskEnum.ORDER.getTaskType()), orderListener);
    }

    /**
     * 商品消费
     */
    @PostConstruct
    public void productConsume() {
        log.info("初始化商品消费");
        consume(properties.getMap().get(TaskEnum.PRODUCT.getTaskType()), productListener);
    }

    /**
     * RocketMQ消费消息
     *
     * @param properties
     * @param listener
     */
    private void consume(RocketMQProperties.BaseProperties properties, MessageListenerConcurrently listener) {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(properties.getGroup());
            consumer.setNamesrvAddr(properties.getAddress());
            consumer.subscribe(properties.getTopic(), "");
            log.info("地址：{}，分组：{}，主题：{}", properties.getAddress(), properties.getGroup(), properties.getTopic());
            consumer.registerMessageListener(listener);
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RocketMQ消费消息失败：{}", e);
        }
    }
}