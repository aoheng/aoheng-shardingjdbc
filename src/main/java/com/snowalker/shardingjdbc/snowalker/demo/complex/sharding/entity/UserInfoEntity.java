package com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/3/23 10:48
 * @className UserInfoEntity
 * @desc 用户实体
 */
@Data
@TableName("t_user")
public class UserInfoEntity {

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
