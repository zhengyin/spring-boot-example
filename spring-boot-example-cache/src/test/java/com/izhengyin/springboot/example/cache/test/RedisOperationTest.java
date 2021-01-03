package com.izhengyin.springboot.example.cache.test;

import com.izhengyin.springboot.example.cache.config.RedisMessageListenerConfiguration;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.test.web.reactive.server.FluxExchangeResult;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Create on 2021/1/3 3:55 下午
 */
@SpringBootTest
public class RedisOperationTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 分布式锁
     * @throws InterruptedException
     */
    @Test
    public void lock() throws InterruptedException{
        String key = "LOCK";
        AtomicInteger counter = new AtomicInteger();
        CountDownLatch downLatch = new CountDownLatch(3);
        //并发获取锁
        ExecutorService executors = Executors.newFixedThreadPool(3);
        IntStream.range(0,3).forEach(i -> executors.execute(() -> {
                    try {
                        //锁3秒超时
                        boolean obtainLock = Optional.ofNullable(stringRedisTemplate.opsForValue()
                                .setIfAbsent(key,"1",3, TimeUnit.SECONDS))
                                .orElse(false);
                        if(obtainLock){
                            counter.getAndIncrement();
                        }
                        try {
                            //模拟业务操作耗时
                            TimeUnit.SECONDS.sleep(1);
                        }catch (InterruptedException e){

                        }
                    }finally {
                        stringRedisTemplate.delete(key);
                        downLatch.countDown();
                    }
                })
        );
        downLatch.await();
        Assert.assertEquals(1,counter.get());
    }

    /**
     * 计数
     */
    @Test
    public void counter(){
        String inventoryKey = "inventory";
        IntStream.range(0,5).forEach(i -> stringRedisTemplate.opsForValue().increment(inventoryKey));
        IntStream.range(0,3).forEach(i -> stringRedisTemplate.opsForValue().decrement(inventoryKey));
        Assert.assertEquals("2",stringRedisTemplate.opsForValue().get(inventoryKey));
    }

    @Test
    public void pubsub(){
        Stream.of(1,2,3,4,5)
                .map(i -> {
                    //每秒发送一个
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    }catch (InterruptedException e){}
                    return "order id  "+i;
                })
                .forEach(msg -> {
                    stringRedisTemplate.convertAndSend(RedisMessageListenerConfiguration.TOPIC,msg);
                });
    }
}
