package com.izhengyin.springboot.example.atomikos.dao.mapper.order;
import com.izhengyin.springboot.example.atomikos.dao.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-05 14:22
 */
@Component
@Mapper
public interface OrderMapper {

    @Select("SELECT COUNT(*) FROM t_order where item_id=#{itemId}")
    public int countByItemId(@Param("itemId") int itemId);

    /**
     * 删除数据
     * @param itemId
     * @return
     */
    @Delete("DELETE FROM t_order where item_id=#{itemId}")
    public int del(@Param("itemId") int itemId);

    /**
     * 清空数据
     * @return
     */
    @Delete("DELETE FROM t_order")
    public int delAll();

    /**
     * @param sellerId
     * @param itemId
     * @return
     */
    @Insert("INSERT INTO t_order(seller_id,item_id) VALUES(#{sellerId},#{itemId})")
    public int add(@Param("sellerId") int sellerId , @Param("itemId") int itemId);

    /**
     * 获取列表
     * @param offset
     * @param size
     * @return
     */
    @Select("SELECT * FROM t_order ORDER BY order_id ASC LIMIT #{offset} , #{size}")
    public List<Order> getList(@Param("offset") int offset, @Param("size") int size);
}
