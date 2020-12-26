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

    /**
     * 数据不会被删除
     * @param timeout
     * @param blogId
     * @return
     */
    @Transactional(rollbackFor = Exception.class , propagation = Propagation.REQUIRED,timeout = 3)
    public int timeout(int timeout,int blogId){
        jdbcTemplate.execute("DELETE FROM blog where id = "+blogId);
        Sleep.second(timeout);
        jdbcTemplate.execute("SELECT 1");
        return timeout;
    }

    /**
     * 数据会被删除
     * @param timeout
     * @param blogId
     * @return
     */
    @Transactional(rollbackFor = Exception.class , propagation = Propagation.REQUIRED,timeout = 3)
    public int timeoutNotWork(int timeout , int blogId){
        jdbcTemplate.execute("DELETE FROM blog where id = "+blogId);
        Sleep.second(timeout);
        return timeout;
    }
}
