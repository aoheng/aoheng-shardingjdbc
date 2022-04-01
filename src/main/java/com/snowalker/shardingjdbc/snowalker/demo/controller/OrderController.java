package com.snowalker.shardingjdbc.snowalker.demo.controller;

import com.snowalker.shardingjdbc.snowalker.demo.entity.OrderInfo;
import com.snowalker.shardingjdbc.snowalker.demo.service.OrderService;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<OrderInfo> queryOrderInfoList(OrderInfo orderInfo) {
        return orderService.queryOrderInfoList(orderInfo);
    }

    @GetMapping("/queryOrderInfoByOrderId")
    public OrderInfo queryOrderInfoByOrderId(OrderInfo orderInfo) {
        OrderInfo info= orderService.queryOrderInfoByOrderId(orderInfo);
        return info;
    }


    @PostMapping("/addOrder")
    public int addOrder(OrderInfo orderInfo) {
        return orderService.addOrder(orderInfo);
    }





}
