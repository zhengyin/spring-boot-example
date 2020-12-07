package com.izhengyin.springboot.example.atomikos.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.UserTransaction;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-07 15:50
 */
@Configuration
public class JtaTransactionManagerConfiguration {

    @Bean(name = "atomikosJtaTransactionManager")
    @Primary
    public JtaTransactionManager atomikosJtaTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }
}
