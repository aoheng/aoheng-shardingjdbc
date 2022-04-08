package com.snowalker.shardingjdbc.snowalker.demo.controller;

import com.snowalker.shardingjdbc.snowalker.demo.common.api.ApiPageResult;
import com.snowalker.shardingjdbc.snowalker.demo.common.api.ApiResult;
import com.snowalker.shardingjdbc.snowalker.demo.common.page.Pagination;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.OrderInfo;
import com.snowalker.shardingjdbc.snowalker.demo.pojo.query.OrderQuery;
import com.snowalker.shardingjdbc.snowalker.demo.service.OrderService;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 订单
 * @author: Aoheng
 * @date: 2022/4/1 11:38
 */
@Slf4j
@Api
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/queryOrderInfoList")
    public ApiResult<List<OrderInfo>> queryOrderInfoList(OrderInfo orderInfo) {
        return ApiResult.ok(orderService.queryOrderInfoList(orderInfo));
    }

    @ApiOperation("订单分页列表")
    @GetMapping("/page")
    public ApiPageResult<OrderInfo> page(OrderQuery orderInfo, Pagination pagination) {
        return orderService.page(orderInfo, pagination);
    }

    @GetMapping("/queryOrderInfoByOrderId")
    public ApiResult<OrderInfo> queryOrderInfoByOrderId(OrderInfo orderInfo) {
        return ApiResult.ok(orderService.queryOrderInfoByOrderId(orderInfo));
    }


    @ApiOperation("新增订单")
    @PostMapping("/addOrder")
    public ApiResult<Integer> addOrder(@RequestBody OrderInfo orderInfo) {
        return ApiResult.ok(orderService.addOrder(orderInfo));
    }


}
