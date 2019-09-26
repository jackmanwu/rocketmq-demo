package com.jackmanwu.rocketmq.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jackmanwu
 * @create 2019-09-24 15:30:43
 * @description
 */
@Getter
@AllArgsConstructor
public enum TaskEnum {
    /**
     * 订单数据
     */
    ORDER("order", "订单数据"),
    /**
     * 商品数据
     */
    PRODUCT("product", "商品数据");

    private String taskType;

    private String desc;
}