package com.snowalker.shardingjdbc.snowalker.demo;

import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.constant.DbAndTableEnum;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.entity.OrderNewInfoEntity;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.sequence.KeyGenerator;
import com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.service.OrderNewSerivce;
import com.snowalker.shardingjdbc.snowalker.demo.entity.OrderInfo;
import com.snowalker.shardingjdbc.snowalker.demo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class SnowalkerShardingjdbcDemoApplicationTests {


    @Resource(name = "orderService")
    OrderService orderService;
    @Autowired
    KeyGenerator keyGenerator;

    //@Test
    public void testInsertOrderInfo() {
        for (int i = 1; i < 10; i++) {
            long userId = i;
            //long orderId = KeyGenerator.generateKey(DbAndTableEnum.T_USER,"2");
            long orderId = KeyGenerator.getSnowFlakeId();
            //long orderId = 4*i + 1;
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setUserName("aoheng" + i);
            orderInfo.setUserId(userId);
            orderInfo.setOrderId(orderId);
            int result = orderService.addOrder(orderInfo);
            if (1 == result) {
                log.info("入库成功,orderInfo={}", orderInfo);
            } else {
                log.info("入库失败,orderInfo={}", orderInfo);
            }
        }
    }

    /**
     * 默认规则跨片归并
     */
    //@Test
    public void testQueryList() {
        List<OrderInfo> list = new ArrayList<>();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(2l);
        orderInfo.setUserId(2l);
        list = orderService.queryOrderInfoList(orderInfo);
        log.info("查询结果:{}", list.toString());
    }


    //@Test
    public void testQueryById() {
        OrderInfo queryParam = new OrderInfo();
        queryParam.setUserId(8l);
        queryParam.setOrderId(8l);
        OrderInfo queryResult = orderService.queryOrderInfoByOrderId(queryParam);
        if (queryResult != null) {
            log.info("查询结果:orderInfo={}", queryResult);
        } else {
            log.info("查无此记录");
        }
    }


    /**
     * 测试分布式主键生成
     */
    //@Test
    public void testGenerateId() {
        // 支付宝或者微信uid
        String outId = "1232132131241241243123";
        log.info("获取id开始");
        String innerUserId = keyGenerator.generateKey(DbAndTableEnum.T_USER, outId);
        log.info("外部id={},innerUserId={}", outId, innerUserId);
        String orderId = keyGenerator.generateKey(DbAndTableEnum.T_NEW_ORDER, innerUserId);
        log.info("外部id={},innerUserId={},orderId={}", outId, innerUserId, orderId);
    }


    @Autowired
    OrderNewSerivce orderNewSerivce;

    /**
     * 测试新的订单入库
     */
    //@Test
    public void testNewOrderInsert() {
        // 支付宝或者微信uid
        for (int i = 0; i < 1; i++) {
            String outId = "1232132131241241243126" + i;
            log.info("获取id开始");
            String innerUserId = keyGenerator.generateKey(DbAndTableEnum.T_USER, outId);
            log.info("外部id={},内部用户={}", outId, innerUserId);
            String orderId = keyGenerator.generateKey(DbAndTableEnum.T_NEW_ORDER, innerUserId);
            log.info("外部id={},内部用户={},订单={}", outId, innerUserId, orderId);
            OrderNewInfoEntity orderInfo = new OrderNewInfoEntity();
            orderInfo.setUserName("snowalker");
            orderInfo.setUserId(innerUserId);
            orderInfo.setOrderId(orderId);
            orderNewSerivce.addOrder(orderInfo);
        }

    }

    /**
     * 测试订单明细查询
     */
    //@Test
    public void testQueryNewOrderById() {
        String orderId = "OD010001011903261549424993200011";
        String userId = "UD030001011903261549424973200007";
        OrderNewInfoEntity orderInfo = new OrderNewInfoEntity();
        orderInfo.setOrderId(orderId);
        orderInfo.setUserId(userId);
        System.out.println(orderNewSerivce.queryOrderInfoByOrderId(orderInfo));
    }

    /**
     * 测试订单列表查询
     */
    //@Test
    public void testQueryNewOrderList() {
        String userId = "UD030001011903261549424973200007";
        OrderNewInfoEntity orderInfo = new OrderNewInfoEntity();
        orderInfo.setUserId(userId);
        List<OrderNewInfoEntity> list = new ArrayList<>();
        list = orderNewSerivce.queryOrderInfoList(orderInfo);
        System.out.println(list);
    }
}
