package com.jackmanwu.rocketmq.demo.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jackmanwu
 * @create 2019-09-25 15:46:59
 * @description
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMQProperties {
    private final Map<String, BaseProperties> map = new HashMap<>();

    @Setter
    @Getter
    public static class BaseProperties {
        private String group;

        private String address;

        private String topic;
    }
}
