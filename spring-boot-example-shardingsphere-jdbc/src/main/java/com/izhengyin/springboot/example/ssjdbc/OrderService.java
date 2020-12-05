package com.izhengyin.springboot.example.ssjdbc;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.izhengyin.springboot.example.ssjdbc.dao.entity.Order;
import com.izhengyin.springboot.example.ssjdbc.dao.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-05 14:21
 */
@Service
@Slf4j
public class OrderService {
    private final OrderMapper orderMapper;
    public OrderService(OrderMapper orderMapper){
        this.orderMapper = orderMapper;
    }

    /**
     * 初始化数据
     */
    @PostConstruct
    public void postConstruct(){
        IntStream.range(1,10)
                .forEach(i -> {
                    int sellerId = i % 3;
                    int orderId = i ;
                    orderMapper.delete(
                            Wrappers.<Order>lambdaUpdate()
                                    .eq(Order::getSellerId,sellerId)
                                    .eq(Order::getOrderId,orderId)
                    );
                    Order order = new Order();
                    order.setOrderId(orderId);
                    order.setSellerId(sellerId);
                    order.setBuyerId(0);
                    orderMapper.insert(order);
                });
    }


    /**
     * 统计总数
     * @return
     */
    public int count(){
        return orderMapper.selectCount(Wrappers.lambdaQuery());
    }

    /**
     * SELECT * FROM t_order ORDER BY order_id ASC LIMIT #{offset} , #{size}
     */
    public List<Order> getList(int offset , int size){
        return orderMapper.getList(offset, size);
    }

    /**
     * 删除多个
     * @param orderIds
     * @return
     */
    public int delMulti(List<Integer> orderIds){
        return orderMapper.deleteBatchIds(orderIds);
    }

    /**
     * 删除多个
     * @return
     */
    public boolean exists(int  orderId){
        return Objects.nonNull(orderMapper.selectById(orderId));
    }



}
