package com.snowalker.shardingjdbc.snowalker.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 订单实体
 * @author: Aoheng
 * @date: 2022/4/2 15:15
 */
@Data
public class OrderInfo implements Serializable {

    private Long id;
    private Long userId;
    private Long orderId;
    private String userName;

}
