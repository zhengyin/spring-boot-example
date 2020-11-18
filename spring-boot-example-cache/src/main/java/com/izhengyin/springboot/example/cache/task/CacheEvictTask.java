package com.izhengyin.springboot.example.cache.task;
import com.izhengyin.springboot.example.cache.service.CacheEvictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2019-11-07 16:47
 */
@Component
public class CacheEvictTask {
    public static final String NAME = "kongfz";
    public final CacheEvictService cacheEvictService;
    private final Logger logger = LoggerFactory.getLogger(CacheEvictTask.class);
    public CacheEvictTask(CacheEvictService cacheEvictService){
        this.cacheEvictService = cacheEvictService;
    }

    @PostConstruct
    public void setUp(){
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.info("schedule executing ... ");
                cacheEvictService.noMatterName(NAME);
            }
        },5000,5000 , TimeUnit.MILLISECONDS);

    }
}
