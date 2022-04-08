package com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 复合分片订单实体
 * @author: Aoheng
 * @date: 2022/4/2 15:15
 */
@Data
@TableName("t_new_order")
public class OrderNewInfoEntity implements Serializable {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 用户名称
     */
    private String userName;

}
