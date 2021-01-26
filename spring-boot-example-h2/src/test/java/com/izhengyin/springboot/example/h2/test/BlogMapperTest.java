package com.izhengyin.springboot.example.h2.test;

import com.izhengyin.springboot.example.h2.Application;
import com.izhengyin.springboot.example.h2.dao.entity.Blog;
import com.izhengyin.springboot.example.h2.dao.mapper.BlogMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021-01-14 13:37
 */
@SpringBootTest(classes = Application.class)
@ActiveProfiles("h2")
public class BlogMapperTest {
    @Autowired
    private BlogMapper blogMapper;
    @Test
    public void testSelect(){

        blogMapper.lastInsertId();

        Blog blog = blogMapper.selectById(1);
        Assert.assertNotNull(blog);
    }
}