package com.snowalker.shardingjdbc.snowalker.demo.controller;

import com.snowalker.shardingjdbc.snowalker.demo.common.api.ApiPageResult;
import com.snowalker.shardingjdbc.snowalker.demo.common.api.ApiResult;
import com.snowalker.shardingjdbc.snowalker.demo.common.page.Pagination;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.OrderNewInfoEntity;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.service.OrderNewSerivce;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.query.NewOrderQuery;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 新用户订单（复合分片）
 * @author: Aoheng
 * @date: 2022/4/1 11:38
 */
@Slf4j
@Api
@RestController
@RequestMapping("/newOrder")
public class NewOrderController {

    private final OrderNewSerivce orderNewSerivce;

    @Autowired
    public NewOrderController(OrderNewSerivce orderNewSerivce) {
        this.orderNewSerivce = orderNewSerivce;
    }


    @GetMapping("/list")
    public ApiResult<List<OrderNewInfoEntity>> list(NewOrderQuery orderInfo) {
        return ApiResult.ok(orderNewSerivce.list(orderInfo));
    }

    @ApiOperation("订单分页列表")
    @GetMapping("/page")
    public ApiPageResult<OrderNewInfoEntity> page(NewOrderQuery query, Pagination pagination) {
        return orderNewSerivce.page(query, pagination);
    }

    @GetMapping("/queryByOrderId")
    public ApiResult<List<OrderNewInfoEntity>> queryByOrderId(NewOrderQuery orderInfo) {
        return ApiResult.ok(orderNewSerivce.queryOrderInfoByOrderId(orderInfo));
    }


    @ApiOperation("新增订单")
    @PostMapping("/addOrder")
    public ApiResult<Integer> addOrder(@RequestBody OrderNewInfoEntity orderInfo) {
        return ApiResult.ok(orderNewSerivce.addOrder(orderInfo));
    }


}
