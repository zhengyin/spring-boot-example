package com.izhengyin.springboot.example.ssjdbc.dao.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.izhengyin.springboot.example.ssjdbc.dao.entity.Order;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-05 14:22
 */
@Component
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Delete("DELETE FROM t_order where ( seller_id=3)")
    public int del();

    @Select("SELECT * FROM t_order ORDER BY order_id ASC LIMIT #{offset} , #{size}")
    public List<Order> getList(@Param("offset") int offset , @Param("size") int size);
}
