package com.jackmanwu.rocketmq.demo.listener;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author jackmanwu
 * @create 2019-09-25 17:10:06
 * @description
 */
@Log4j2
@Component
public class ProductListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        log.info("Product: {} Receive New Messages: {}", Thread.currentThread().getName(), list);
        try {
            String msg = new String(list.get(0).getBody(), RemotingHelper.DEFAULT_CHARSET);
            log.info("Product：{}", msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
