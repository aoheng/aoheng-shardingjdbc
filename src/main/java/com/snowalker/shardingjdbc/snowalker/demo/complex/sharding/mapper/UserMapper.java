package com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 用户UserMapper
 * @author: Aoheng
 * @date: 2022/4/2 15:08
 */
@Mapper
public interface UserMapper extends BaseMapper<UserInfoEntity> {


}
