package com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 订单实体
 * @author: Aoheng
 * @date: 2022/4/2 15:15
 */
@Data
@TableName("t_order")
public class OrderInfo implements Serializable {

    private Long id;
    private Long userId;
    private Long orderId;
    private String userName;

}
