package com.izhengyin.springboot.example.transaction.test;

import com.izhengyin.springboot.example.transaction.dao.entity.Blog;
import com.izhengyin.springboot.example.transaction.service.TransactionExampleService;
import com.izhengyin.springboot.example.transaction.service.TransactionPropagationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.TransientDataAccessResourceException;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-01 13:42
 */
@SpringBootTest
@Slf4j
public class TransactionExampleServiceTest {

    @Autowired
    private TransactionExampleService transactionExampleService;
    @Autowired
    private TransactionPropagationService transactionPropagationService;

    @Test
    public void readOnlyTest(){
        for (int i=1;i<=5;i++){
            if(i % 5 == 0){
                final int n = i;
                Assert.assertThrows(TransientDataAccessResourceException.class,() -> {
                    transactionExampleService.readOnly(n);
                });
            }else{
                Blog blog = transactionExampleService.readOnly(i);
                Assert.assertNotNull(blog);
                Assert.assertEquals(blog.getId().intValue(),i);

            }
        }
    }


    @Test
    public void rollbackTest(){
        int mark = 100;
        for (int i=90;i<=mark;i++){
            if(i % mark == 0){
                Assert.assertTrue(transactionExampleService.rollback(i));
                Assert.assertFalse(transactionExampleService.exists(i));
            }else{
                final int id = i;
                Assert.assertThrows(IllegalArgumentException.class,() -> {
                    transactionExampleService.rollback(id);
                    Assert.assertTrue(transactionExampleService.exists(id));
                });
            }
        }
    }


    @Test
    public void propagationRequiredTest(){
        Assert.assertThrows(RuntimeException.class,() -> transactionPropagationService.required());
        Assert.assertTrue(transactionExampleService.exists(1));
        Assert.assertTrue(transactionExampleService.exists(2));
        Assert.assertTrue(transactionExampleService.exists(3));
    }


    @Test
    public void propagationNotSupportTest(){
        Assert.assertThrows(RuntimeException.class,() -> transactionPropagationService.notSupport());
        Assert.assertTrue(transactionExampleService.exists(1));
        Assert.assertTrue(transactionExampleService.exists(2));
        Assert.assertFalse(transactionExampleService.exists(3));
    }

}
