package com.izhengyin.springboot.example.transaction.service;

import com.izhengyin.springboot.example.transaction.Sleep;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-25 17:18
 */
@Service
public class TransactionTimeoutService {
    private final TransactionExampleService transactionExampleService;
    private final JdbcTemplate jdbcTemplate;
    public TransactionTimeoutService(TransactionExampleService transactionExampleService,JdbcTemplate jdbcTemplate){
        this.transactionExampleService = transactionExampleService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(rollbackFor = Exception.class , propagation = Propagation.REQUIRED,timeout = 3)
    public int timeout(int timeout){
        jdbcTemplate.execute("SELECT 1");
        Sleep.second(timeout);
        jdbcTemplate.execute("SELECT 1");
        return timeout;
    }

    @Transactional(rollbackFor = Exception.class , propagation = Propagation.REQUIRED,timeout = 3)
    public int timeoutNotWork(int timeout , int delId){
        jdbcTemplate.execute("DELETE FROM blog where id = "+delId);
        Sleep.second(timeout);
        return timeout;
    }
}
