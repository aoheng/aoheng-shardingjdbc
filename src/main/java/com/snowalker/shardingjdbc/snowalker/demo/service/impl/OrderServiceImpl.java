package com.snowalker.shardingjdbc.snowalker.demo.service.impl;

import com.snowalker.shardingjdbc.snowalker.demo.entity.OrderInfo;
import com.snowalker.shardingjdbc.snowalker.demo.mapper.OrderMapper;
import com.snowalker.shardingjdbc.snowalker.demo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/3/3 22:39
 * @className
 * @desc
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderInfo> queryOrderInfoList(OrderInfo orderInfo) {
        return orderMapper.queryOrderInfoList(orderInfo);
    }

    @Override
    public OrderInfo queryOrderInfoByOrderId(OrderInfo orderInfo) {
        return orderMapper.queryOrderInfoByOrderId(orderInfo);
    }

    @Override
    public int addOrder(OrderInfo orderInfo) {
        log.info("订单入库开始，orderinfo={}", orderInfo.toString());
        return orderMapper.addOrder(orderInfo);
    }
}
