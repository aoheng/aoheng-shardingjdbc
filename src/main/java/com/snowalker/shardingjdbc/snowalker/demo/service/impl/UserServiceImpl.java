package com.snowalker.shardingjdbc.snowalker.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.constant.DbAndTableEnum;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.UserInfoEntity;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.mapper.UserMapper;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.sequence.KeyGenerator;
import com.snowalker.shardingjdbc.snowalker.demo.mapstruct.UserMapstruct;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.model.UserModel;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.query.UserQuery;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.vo.UserVO;
import com.snowalker.shardingjdbc.snowalker.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Aoheng
 * @date: 2022/4/2 14:31
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    UserMapstruct userMapstruct = UserMapstruct.INSTANCE;

    private final UserMapper userMapper;
    private final KeyGenerator keyGenerator;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, KeyGenerator keyGenerator) {
        this.userMapper = userMapper;
        this.keyGenerator = keyGenerator;
    }


    @Override
    public int addUserInfo(UserModel userModel) {
        UserInfoEntity userInfo = userMapstruct.converToEntity(userModel);
        userInfo.setUserId(keyGenerator.generateKey(DbAndTableEnum.T_USER, userInfo.getUserOutId()));
        log.info("userInfo:{}", userInfo);
        return userMapper.insert(userInfo);
    }

    @Override
    public UserVO updateUserInfo(UserModel userModel) {
        UserInfoEntity userInfo = userMapstruct.converToEntity(userModel);
        userMapper.updateById(userInfo);
        return userMapstruct.converToVo(userInfo);
    }

    @Override
    public UserVO getUserInfo(UserQuery userModel) {
        UserInfoEntity user = new UserInfoEntity();
        user.setUserId(userModel.getUserId());
        UserInfoEntity userInfo = userMapper.selectOne(new QueryWrapper<UserInfoEntity>().setEntity(user));
        return userMapstruct.converToVo(userInfo);
    }

    @Override
    public List<UserVO> getUserList(UserQuery userQuery) {
        List<UserInfoEntity> list = userMapper.selectList(new QueryWrapper<UserInfoEntity>().orderByDesc("id"));
        return userMapstruct.converToVoList(list);
    }

}
