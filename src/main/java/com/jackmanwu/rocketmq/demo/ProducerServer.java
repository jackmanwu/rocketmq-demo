package com.jackmanwu.rocketmq.demo;


import com.jackmanwu.rocketmq.demo.config.RocketMQConsumerConfig;
import com.jackmanwu.rocketmq.demo.config.RocketMQProducerConfig;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.Scanner;

/**
 * @author jackmanwu
 * @create 2019-09-25 17:44:48
 * @description
 */
@SpringBootApplication
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {RocketMQConsumerConfig.class, ConsumerServer.class})})
public class ProducerServer {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(ProducerServer.class);
        RocketMQProducerConfig.Producer orderProduce = context.getBean("orderProduce", RocketMQProducerConfig.Producer.class);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (!"quit".equals(line)) {
                orderProduce.getProducer().send(new Message(orderProduce.getTopic(), line.getBytes(RemotingHelper.DEFAULT_CHARSET)));
            } else {
                break;
            }
        }
    }
}
