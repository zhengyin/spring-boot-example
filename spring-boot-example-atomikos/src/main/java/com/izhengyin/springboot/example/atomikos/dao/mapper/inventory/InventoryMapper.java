package com.izhengyin.springboot.example.atomikos.dao.mapper.inventory;
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
public interface InventoryMapper {

    /**
     * 通过itemId统计总数
     * @param itemId
     * @return
     */
    @Select("SELECT COUNT(*) FROM t_inventory where item_id=#{itemId}")
    public int countByItemId(@Param("itemId") int itemId);

    /**
     * 清空数据
     * @return
     */
    @Delete("DELETE FROM t_inventory")
    public int delAll();

    /**
     * 删除数据
     * @param itemId
     * @return
     */
    @Delete("DELETE FROM t_inventory where item_id=#{itemId}")
    public int del(@Param("itemId") int itemId);

    /**
     * @param itemId
     * @param amount
     * @return
     */
    @Insert("INSERT INTO t_inventory(item_id,amount) VALUES(#{itemId},#{amount})")
    public int add( @Param("itemId") int itemId,@Param("amount") int amount);

    /**
     * amount
     * @return
     */
    @Select("SELECT amount FROM t_inventory WHERE item_id=#{itemId}")
    public int getAmount(@Param("itemId") int itemId);

    /**
     * amount
     * @return
     */
    @Select("UPDATE t_inventory SET amount = #{amount}  WHERE item_id=#{itemId}")
    public Integer setAmount(@Param("itemId") int itemId,@Param("amount") int amount);

    /**
     * 获取列表
     * @param offset
     * @param size
     * @return
     */
    @Select("SELECT * FROM t_inventory ORDER BY id ASC LIMIT #{offset} , #{size}")
    public List<Order> getList(@Param("offset") int offset, @Param("size") int size);
}
