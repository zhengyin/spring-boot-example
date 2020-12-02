package com.izhengyin.springboot.example.transaction.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.izhengyin.springboot.example.transaction.dao.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-01 15:13
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

}
