package com.jackmanwu.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author jackmanwu
 * @create 2019-09-25 09:49:05
 * @description
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("jackmanwu-test");
        producer.setNamesrvAddr("master:9876");
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message message = new Message("jackmanwu-topic",  ("Hello,RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(message);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}
