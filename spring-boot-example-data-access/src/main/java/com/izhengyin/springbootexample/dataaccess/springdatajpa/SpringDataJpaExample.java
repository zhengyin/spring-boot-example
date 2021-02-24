package com.izhengyin.springbootexample.dataaccess.springdatajpa;

import com.izhengyin.springbootexample.dataaccess.entity.Blog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021-02-24 14:04
 */
@Component
@Slf4j
public class SpringDataJpaExample{

    private final BlogRepository blogRepository;

    public SpringDataJpaExample(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @PostConstruct
    public void construct(){
        Page<Blog> blogPage = this.blogRepository.findAll(PageRequest.of(0,10));
        log.info("blogPage.getTotalElements() "+blogPage.getTotalElements());
        log.info("blogPage.getTotalPages() "+blogPage.getTotalPages());
        blogPage.getContent().stream().map(v -> v.getId()+" => "+v.getTitle()).forEach(log::info);
    }
}