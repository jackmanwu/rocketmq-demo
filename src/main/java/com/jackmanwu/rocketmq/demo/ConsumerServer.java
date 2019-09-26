package com.jackmanwu.rocketmq.demo;

import com.jackmanwu.rocketmq.demo.config.RocketMQProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author jackmanwu
 * @create 2019-09-24 13:50:34
 * @description
 */
@SpringBootApplication
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {RocketMQProducerConfig.class, ProducerServer.class})})
public class ConsumerServer {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerServer.class);
    }
}