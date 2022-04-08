package com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.OrderNewInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/3/23 10:52
 * @className OrderNewMapper
 * @desc 订单 new Mapper
 */
@Mapper
public interface OrderNewMapper extends BaseMapper<OrderNewInfoEntity> {

    List<OrderNewInfoEntity> queryOrderInfoList(OrderNewInfoEntity orderInfo);

    OrderNewInfoEntity queryOrderInfoByOrderId(OrderNewInfoEntity orderInfo);

    int addOrder(OrderNewInfoEntity orderInfo);
}
