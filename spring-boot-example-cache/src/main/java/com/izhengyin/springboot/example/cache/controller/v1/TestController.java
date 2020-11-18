package com.izhengyin.springboot.example.cache.controller.v1;

import com.izhengyin.springboot.example.cache.service.CacheEvictService;
import com.kongfz.util.cache.constant.CacheDrive;
import com.kongfz.util.cache.constant.CacheName;
import com.kongfz.util.cache.constant.GeneratorName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2019-11-05 17:41
 */
@RestController
public class TestController {

    @Value("${spring.application.name}")
    private String application;
    private final CacheEvictService cacheEvictService;
    public TestController(CacheEvictService cacheEvictService){
        this.cacheEvictService = cacheEvictService;
    }

    @Cacheable(cacheNames = {CacheName.Redis.TTL_5} , keyGenerator = GeneratorName.DEFAULT , cacheManager = CacheDrive.REDIS)
    @GetMapping("/hello/{name}")
    public Object hello(@PathVariable String name){
        return "Hello "+name+" , I am "+application +" , time "+System.currentTimeMillis();
    }

    @PutMapping("/helloCacheEvict/{name}")
    public void helloCacheEvict(@PathVariable String name){
        cacheEvictService.hello(name);
    }


    /**
     * 我们可以为耗时的接口使用内存缓存，来防止并发
     * @param timeout
     * @return
     * @throws InterruptedException
     */
    @Cacheable(cacheNames = {CacheName.Caffeine.TTL_1} , keyGenerator = GeneratorName.DEFAULT , cacheManager = CacheDrive.CAFFEINE)
    @GetMapping("/timeout/{timeout}")
    public Object timeout(@PathVariable int timeout) throws InterruptedException{
        TimeUnit.MILLISECONDS.sleep(timeout);
        return System.currentTimeMillis();
    }


}