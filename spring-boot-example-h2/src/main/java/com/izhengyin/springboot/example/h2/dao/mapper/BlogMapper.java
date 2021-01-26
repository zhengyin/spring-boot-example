package com.izhengyin.springboot.example.h2.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.izhengyin.springboot.example.h2.dao.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-01 15:13
 */
@Mapper
@Component
public interface BlogMapper extends BaseMapper<Blog> {

    @Select("SELECT LAST_INSERT_ID()")
    public Integer lastInsertId();
}
