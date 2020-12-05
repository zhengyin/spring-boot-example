package com.izhengyin.springboot.example.ssjdbc.test;

import com.alibaba.fastjson.JSON;
import com.izhengyin.springboot.example.ssjdbc.OrderService;
import com.izhengyin.springboot.example.ssjdbc.dao.entity.Order;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-05 14:19
 */
@SpringBootTest
public class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void getCount(){
        Assert.assertEquals(orderService.count(),9);
    }

    @Test
    public void getList(){
        List<Order> orderList = orderService.getList(5,2);
        System.out.println(JSON.toJSONString(orderList));
        Assert.assertEquals(orderList.size(),2);
        Assert.assertEquals(orderList.get(0).getOrderId().intValue(),6);
        Assert.assertEquals(orderList.get(1).getOrderId().intValue(),7);
    }

    @Test
    public void delMulti(){
         orderService.delMulti(Arrays.asList(1,2,3,4));
         Assert.assertFalse(orderService.exists(1));
         Assert.assertFalse(orderService.exists(2));
         Assert.assertFalse(orderService.exists(3));
         Assert.assertFalse(orderService.exists(4));
    }


}
