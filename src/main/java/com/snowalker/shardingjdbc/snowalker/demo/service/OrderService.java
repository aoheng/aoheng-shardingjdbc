package com.snowalker.shardingjdbc.snowalker.demo.service;

import com.snowalker.shardingjdbc.snowalker.demo.common.api.ApiPageResult;
import com.snowalker.shardingjdbc.snowalker.demo.common.page.Pagination;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.OrderInfo;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.query.OrderQuery;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/3/3 22:39
 * @className OrderService
 * @desc
 */
public interface OrderService {

    List<OrderInfo> queryOrderInfoList(OrderInfo orderInfo);

    OrderInfo queryOrderInfoByOrderId(OrderInfo orderInfo);

    int addOrder(OrderInfo orderInfo);

    ApiPageResult<OrderInfo> page(OrderQuery orderInfo, Pagination pagination);
}
