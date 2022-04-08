package com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.service;

import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.OrderNewInfoEntity;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.mapper.OrderNewMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/3/23 10:53
 * @className OrderNewSerivce
 * @desc
 */
@Slf4j
@Service
public class OrderNewSerivce {


    @Autowired
    OrderNewMapper orderNewMapper;

    public List<OrderNewInfoEntity> queryOrderInfoList(OrderNewInfoEntity orderInfo) {
        return orderNewMapper.queryOrderInfoList(orderInfo);
    }

    public OrderNewInfoEntity queryOrderInfoByOrderId(OrderNewInfoEntity orderInfo) {
        return orderNewMapper.queryOrderInfoByOrderId(orderInfo);
    }

    public int addOrder(OrderNewInfoEntity orderInfo) {
        log.info("订单入库开始，orderinfo={}", orderInfo.toString());
        return orderNewMapper.addOrder(orderInfo);
    }
}
