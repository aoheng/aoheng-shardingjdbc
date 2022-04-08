package com.snowalker.shardingjdbc.snowalker.demo.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 用户DTO
 * @author: Aoheng
 * @date: 2022/4/2 15:15
 */
@Data
public class UserDTO implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 外部关联Id
     */
    private String userOutId;
}
