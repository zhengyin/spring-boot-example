package com.izhengyin.springboot.example.transaction.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-01 15:12
 */
@Configuration
@MapperScan("com.izhengyin.springboot.example.transaction.dao.mapper")
public class Scan {

}
