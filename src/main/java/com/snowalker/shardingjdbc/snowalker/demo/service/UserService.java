package com.snowalker.shardingjdbc.snowalker.demo.service;

import com.snowalker.shardingjdbc.snowalker.demo.pojo.model.UserModel;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.query.UserQuery;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.vo.UserVO;

import java.util.List;

/**
 * @description: 用户管理
 * @author: Aoheng
 * @date: 2022/4/2 14:31
 */
public interface UserService {


    int addUserInfo(UserModel userInfo);

    UserVO updateUserInfo(UserModel userModel);

    UserVO getUserInfo(UserQuery userModel);

    List<UserVO> getUserList(UserQuery userQuery);
}
