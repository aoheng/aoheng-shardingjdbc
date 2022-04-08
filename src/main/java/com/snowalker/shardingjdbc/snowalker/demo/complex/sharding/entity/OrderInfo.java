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

    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 用户名称
     */
    private String userName;

}
