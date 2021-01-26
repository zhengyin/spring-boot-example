package com.izhengyin.springboot.example.transaction.service;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Preconditions;
import com.izhengyin.springboot.example.transaction.dao.entity.Blog;
import com.izhengyin.springboot.example.transaction.dao.mapper.BlogMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-01 13:41
 */
@Service
public class TransactionExampleService  {
    private final JdbcTemplate jdbcTemplate;
    private final BlogMapper blogMapper;
    public TransactionExampleService(JdbcTemplate jdbcTemplate, BlogMapper blogMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.blogMapper = blogMapper;
    }

    @Transactional(rollbackFor = Exception.class , isolation = Isolation.REPEATABLE_READ)
    public boolean rollback(int id){
        blogMapper.deleteById(id);
        Preconditions.checkArgument(id % 100 == 0 , "id % 100 ！= 0",id);
        return true;
    }

    @Transactional(rollbackFor = Exception.class , isolation = Isolation.REPEATABLE_READ , readOnly = true)
    public Blog readOnly(int id){
        Blog blog = blogMapper.selectById(id);
        if(id % 5 == 0){
            Blog newBlog = new Blog();
            newBlog.setTitle("new-title-"+id);
            blogMapper.update(newBlog, Wrappers.<Blog>lambdaQuery().eq(Blog::getId,id));
        }
        return blog;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean PROPAGATION_REQUIRED(int id , boolean throwException){
        blogMapper.deleteById(id);
        if(throwException){
            throw new RuntimeException();
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public boolean PROPAGATION_NOT_SUPPORTED(int id, boolean throwException){
        blogMapper.deleteById(id);
        if(throwException){
            throw new RuntimeException();
        }
        return true;
    }

    public boolean exists(int id){
        return Objects.nonNull(blogMapper.selectById(id));
    }
}
