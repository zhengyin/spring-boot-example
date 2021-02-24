package com.izhengyin.springbootexample.dataaccess.springdatajpa;

import com.izhengyin.springbootexample.dataaccess.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021-02-24 14:13
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog,Integer> {}