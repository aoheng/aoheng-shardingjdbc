package com.snowalker.shardingjdbc.snowalker.demo.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 用户VO
 * @author: Aoheng
 * @date: 2022/4/2 15:08
 */
@Data
public class UserVO implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

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
