package com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snowalker.shardingjdbc.snowalker.demo.common.api.ApiPageResult;
import com.snowalker.shardingjdbc.snowalker.demo.common.page.Pagination;
import com.snowalker.shardingjdbc.snowalker.demo.common.page.QueryBuilder;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.constant.DbAndTableEnum;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.OrderNewInfoEntity;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.mapper.OrderNewMapper;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.sequence.KeyGenerator;
import com.snowalker.shardingjdbc.snowalker.demo.mapstruct.NewOrderMapstruct;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.query.NewOrderQuery;
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

    NewOrderMapstruct newOrderMapstruct = NewOrderMapstruct.INSTANCE;


    private final OrderNewMapper orderNewMapper;
    private final KeyGenerator keyGenerator;

    @Autowired
    public OrderNewSerivce(OrderNewMapper orderNewMapper, KeyGenerator keyGenerator) {
        this.orderNewMapper = orderNewMapper;
        this.keyGenerator = keyGenerator;
    }

    public List<OrderNewInfoEntity> list(NewOrderQuery query) {
        OrderNewInfoEntity orderInfo = newOrderMapstruct.converQueryToEntity(query);
        return orderNewMapper.selectList(new QueryWrapper<OrderNewInfoEntity>().setEntity(orderInfo).orderByDesc("id"));
    }

    public List<OrderNewInfoEntity> queryOrderInfoByOrderId(NewOrderQuery query) {
        OrderNewInfoEntity orderInfo = newOrderMapstruct.converQueryToEntity(query);
        return orderNewMapper.selectList(new QueryWrapper<OrderNewInfoEntity>().setEntity(orderInfo));
    }

    public int addOrder(OrderNewInfoEntity orderInfo) {
        orderInfo.setOrderId(keyGenerator.generateKey(DbAndTableEnum.T_NEW_ORDER, orderInfo.getUserId()));
        log.info("订单入库开始，orderinfo={}", orderInfo);
        return orderNewMapper.addOrder(orderInfo);
    }

    public ApiPageResult<OrderNewInfoEntity> page(NewOrderQuery query, Pagination pagination) {
        return ApiPageResult.success(pagination, orderNewMapper.selectList(QueryBuilder.toQueryWrapperPage(query, pagination)), orderNewMapper.selectCount(QueryBuilder.toQueryWrapper(query)));
    }
}
