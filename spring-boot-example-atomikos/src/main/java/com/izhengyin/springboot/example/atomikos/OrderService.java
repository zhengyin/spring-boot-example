package com.izhengyin.springboot.example.atomikos;

import com.izhengyin.springboot.example.atomikos.dao.entity.Order;
import com.izhengyin.springboot.example.atomikos.dao.mapper.inventory.InventoryMapper;
import com.izhengyin.springboot.example.atomikos.dao.mapper.order.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-05 14:21
 */
@Service
@Slf4j
public class OrderService {
    private final OrderMapper orderMapper;
    private final InventoryMapper inventoryMapper;
    public OrderService(OrderMapper orderMapper , InventoryMapper inventoryMapper){
        this.orderMapper = orderMapper;
        this.inventoryMapper = inventoryMapper;
    }

    /**
     * 初始化数据
     */
    @PostConstruct
    public void postConstruct(){
        orderMapper.delAll();
        inventoryMapper.delAll();
        IntStream.range(1,10)
                .forEach(i -> {
                    int sellerId = i % 3;
                    int itemId = i ;
                    orderMapper.add(sellerId,itemId);
                    inventoryMapper.add(itemId,3);
                });
    }


    @Transactional(rollbackFor = RuntimeException.class)
    public void orderOnlyTransaction(int itemId){
        if(orderMapper.del(itemId) > 0){
            throw new RuntimeException("throw exception");
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void inventoryOnlyTransaction(int itemId){
        if(inventoryMapper.del(itemId) > 0){
            throw new RuntimeException("throw exception");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void createOrder(int itemId){
        //演示先增加订单、在扣库存，不要纠结是不是应该先检查库存
        orderMapper.add(1,itemId);

        int amount = inventoryMapper.getAmount(itemId);
        if(amount <= 0){
            throw new RuntimeException("没有库存了！");
        }

        log.info("setAmount {} ",inventoryMapper.setAmount(itemId,amount - 1));
    }
}
