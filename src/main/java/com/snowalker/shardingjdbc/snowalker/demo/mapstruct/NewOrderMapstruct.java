package com.snowalker.shardingjdbc.snowalker.demo.mapstruct;

import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.OrderNewInfoEntity;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.query.NewOrderQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description: MapStruct转换工具类
 * @author: Aoheng
 * @date: 2022/4/2 15:28
 */
@Mapper(componentModel = "Spring")
public interface NewOrderMapstruct {

    NewOrderMapstruct INSTANCE = Mappers.getMapper(NewOrderMapstruct.class);

    OrderNewInfoEntity converQueryToEntity(NewOrderQuery query);
}
