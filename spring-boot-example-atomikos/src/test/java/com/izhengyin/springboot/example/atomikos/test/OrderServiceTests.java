package com.izhengyin.springboot.example.atomikos.test;

import com.izhengyin.springboot.example.atomikos.OrderService;
import com.izhengyin.springboot.example.atomikos.dao.mapper.inventory.InventoryMapper;
import com.izhengyin.springboot.example.atomikos.dao.mapper.order.OrderMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-07 15:56
 */
@SpringBootTest
public class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Test
    public void count(){
        Assert.assertEquals(orderMapper.getList(0,10).size(),9);
        Assert.assertEquals(inventoryMapper.getList(0,10).size(),9);
    }

    @Test
    public void orderOnlyTransaction(){
        Assert.assertThrows(RuntimeException.class, () -> orderService.orderOnlyTransaction(1));
        Assert.assertNotEquals(orderMapper.countByItemId(1),0);
    }

    @Test
    public void inventoryOnlyTransaction() {
        Assert.assertThrows(RuntimeException.class, () -> orderService.inventoryOnlyTransaction(1));
        Assert.assertNotEquals(inventoryMapper.countByItemId(1),0);
    }

    @Test
    public void createOrder() {
        int itemId = 1;
        int orderNum = orderMapper.countByItemId(1);

        orderService.createOrder(itemId);
        Assert.assertEquals(2,inventoryMapper.getAmount(itemId));
        Assert.assertEquals(++orderNum ,orderMapper.countByItemId(itemId));

        orderService.createOrder(itemId);
        Assert.assertEquals(1,inventoryMapper.getAmount(itemId));
        Assert.assertEquals(++orderNum ,orderMapper.countByItemId(itemId));


        orderService.createOrder(itemId);
        Assert.assertEquals(0,inventoryMapper.getAmount(itemId));
        Assert.assertEquals(++orderNum ,orderMapper.countByItemId(itemId));

        Assert.assertThrows(RuntimeException.class, () -> orderService.createOrder(itemId));
        Assert.assertEquals(orderNum,orderMapper.countByItemId(itemId));
    }

}
