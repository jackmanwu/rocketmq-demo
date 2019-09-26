package com.jackmanwu.rocketmq.demo.config;

import com.jackmanwu.rocketmq.demo.config.properties.RocketMQProperties;
import com.jackmanwu.rocketmq.demo.enums.TaskEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jackmanwu
 * @create 2019-09-25 17:19:32
 * @description
 */
@Log4j2
@Configuration
@EnableConfigurationProperties(RocketMQProperties.class)
public class RocketMQProducerConfig {
    @Autowired
    private RocketMQProperties properties;

    /**
     * 订单消息生产
     */
    @Bean
    public Producer orderProduce() {
        log.info("初始化了订单生产者");
        return buildProducer(properties.getMap().get(TaskEnum.ORDER.getTaskType()));
    }

    /**
     * 商品消息生产
     */
    @Bean
    public Producer productProduce() {
        return buildProducer(properties.getMap().get(TaskEnum.PRODUCT.getTaskType()));
    }

    private Producer buildProducer(RocketMQProperties.BaseProperties properties) {
        return new Producer(properties.getTopic(), produce(properties));
    }

    /**
     * RocketMQ生产消息
     *
     * @param properties
     * @return
     */
    private DefaultMQProducer produce(RocketMQProperties.BaseProperties properties) {
        try {
            DefaultMQProducer producer = new DefaultMQProducer(properties.getGroup());
            producer.setNamesrvAddr(properties.getAddress());
            producer.start();
            return producer;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RocketMQ生产消息失败：{}", e);
        }
        return null;
    }

    @Getter
    @AllArgsConstructor
    public class Producer {
        private String topic;

        private DefaultMQProducer producer;
    }
}
