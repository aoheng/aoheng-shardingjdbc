package com.snowalker.shardingjdbc.snowalker.demo.controller;

import com.snowalker.shardingjdbc.snowalker.demo.common.api.ApiResult;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.model.UserModel;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.query.UserQuery;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.vo.UserVO;
import com.snowalker.shardingjdbc.snowalker.demo.service.UserService;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 用户管理
 * @author: Aoheng
 * @date: 2022/4/2 16:40
 */
@Slf4j
@Api
@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation("新增用户")
    @PostMapping()
    public ApiResult<Integer> addUser(@RequestBody UserModel model) {
        return ApiResult.ok(userService.addUserInfo(model));
    }

    @ApiOperation("更新用户")
    @PutMapping(value = "/{id}")
    public ApiResult<UserVO> update(@RequestBody UserModel model) {
        return ApiResult.ok(userService.updateUserInfo(model));
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/userId")
    public ApiResult<UserVO> getByUserId(@RequestParam String userId) {
        return ApiResult.ok(userService.getUserInfo(new UserQuery().setUserId(userId)));
    }

    @ApiOperation("list列表")
    @GetMapping(value = "/list")
    public ApiResult<List<UserVO>> findList(UserQuery query) {
        return ApiResult.ok(userService.getUserList(query));
    }


}
