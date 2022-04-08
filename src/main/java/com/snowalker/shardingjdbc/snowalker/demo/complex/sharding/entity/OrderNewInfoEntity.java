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

    private Long id;
    private String userId;
    private String orderId;
    private String userName;

}
