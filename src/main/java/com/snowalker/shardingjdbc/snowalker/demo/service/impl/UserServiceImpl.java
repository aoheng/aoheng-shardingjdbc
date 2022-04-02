package com.snowalker.shardingjdbc.snowalker.demo.service.impl;

import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.UserInfoEntity;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.mapper.UserMapper;
import com.snowalker.shardingjdbc.snowalker.demo.mapstruct.UserMapstruct;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.model.UserModel;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.query.UserQuery;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.vo.UserVO;
import com.snowalker.shardingjdbc.snowalker.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Aoheng
 * @date: 2022/4/2 14:31
 */
@Service
public class UserServiceImpl implements UserService {

    UserMapstruct userMapstruct = UserMapstruct.INSTANCE;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public int addUserInfo(UserModel userModel) {
        UserInfoEntity userInfo = userMapstruct.converToEntity(userModel);
        return userMapper.insert(userInfo);
    }

    @Override
    public UserVO updateUserInfo(UserModel userModel) {
        UserInfoEntity userInfo = userMapstruct.converToEntity(userModel);
        userMapper.updateByPrimaryKey(userInfo);
        return userMapstruct.converToVo(userInfo);
    }

    @Override
    public UserVO getUserInfo(UserQuery userModel) {
        UserInfoEntity userInfo = userMapper.selectByPrimaryKey(userModel.getId());
        return userMapstruct.converToVo(userInfo);
    }

    @Override
    public List<UserVO> getUserList(UserQuery userQuery) {
        List<UserInfoEntity> list = userMapper.selectByExample(userQuery);
        return userMapstruct.converToVoList(list);
    }

}
