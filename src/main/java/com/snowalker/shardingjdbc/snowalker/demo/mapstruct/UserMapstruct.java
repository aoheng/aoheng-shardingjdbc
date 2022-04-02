package com.snowalker.shardingjdbc.snowalker.demo.mapstruct;

import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.UserInfoEntity;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.model.UserModel;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @description: MapStruct转换工具类
 * @author: Aoheng
 * @date: 2022/4/2 15:28
 */
@Mapper(componentModel = "Spring")
public interface UserMapstruct {

    UserMapstruct INSTANCE = Mappers.getMapper(UserMapstruct.class);


    UserInfoEntity converToEntity(UserModel userModel);

    UserVO converToVo(UserInfoEntity userInfo);

    List<UserVO> converToVoList(List<UserInfoEntity> list);
}
