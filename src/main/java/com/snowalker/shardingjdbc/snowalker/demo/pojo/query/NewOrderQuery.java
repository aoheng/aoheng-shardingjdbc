package com.snowalker.shardingjdbc.snowalker.demo.pojo.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Aoheng
 * @date: 2022/4/8 17:29
 */
@Data
public class NewOrderQuery implements Serializable {

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
