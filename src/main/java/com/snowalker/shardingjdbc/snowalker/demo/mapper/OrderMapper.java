package com.snowalker.shardingjdbc.snowalker.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/3/3 22:40
 * @className OrderMapper
 * @desc
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {

    List<OrderInfo> queryOrderInfoList(OrderInfo orderInfo);

    OrderInfo queryOrderInfoByOrderId(OrderInfo orderInfo);

    int addOrder(OrderInfo orderInfo);
}
