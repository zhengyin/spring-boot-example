package com.izhengyin.springboot.example.transaction.service;

import com.izhengyin.springboot.example.transaction.Sleep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-02 11:08
 */
@Service
public class TransactionPropagationService {
    private final TransactionExampleService transactionExampleService;
    public TransactionPropagationService(TransactionExampleService transactionExampleService){
        this.transactionExampleService = transactionExampleService;
    }
    @Transactional(rollbackFor = Exception.class , propagation = Propagation.REQUIRED)
    public void required(){
        this.transactionExampleService.PROPAGATION_REQUIRED(1,false);
        this.transactionExampleService.PROPAGATION_REQUIRED(2,false);
        this.transactionExampleService.PROPAGATION_REQUIRED(3,true);
    }

    /**
     * id 1 , id 2 使用 required 的传播性 , 因此在 id 3 抛出异常时，可以回滚
     * id 3 使用 not support ，因此无法回滚删除操作，数据会真实被删除。
     */
    @Transactional(rollbackFor = Exception.class , propagation = Propagation.REQUIRED)
    public void notSupport(){
        this.transactionExampleService.PROPAGATION_REQUIRED(1,false);
        this.transactionExampleService.PROPAGATION_REQUIRED(2,false);
        this.transactionExampleService.PROPAGATION_NOT_SUPPORTED(3,true);
    }


}
