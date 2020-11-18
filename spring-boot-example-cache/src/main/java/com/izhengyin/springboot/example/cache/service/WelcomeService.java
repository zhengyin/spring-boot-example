package com.izhengyin.springboot.example.cache.service;

import com.izhengyin.springboot.cache.annotation.CacheTarget;
import com.izhengyin.springboot.cache.constant.CacheDrive;
import com.izhengyin.springboot.cache.constant.CacheName;
import com.izhengyin.springboot.cache.constant.GeneratorName;
import com.izhengyin.springboot.example.cache.config.CacheKeyGeneratorConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2019-11-07 17:13
 */
@Service
public class WelcomeService {

    @Value("${spring.application.name}")
    private String application;

    @CacheTarget(WelcomeService.class)
    @Cacheable(cacheNames = {CacheName.Redis.TTL_60} , keyGenerator = GeneratorName.DEFAULT , cacheManager = CacheDrive.REDIS)
    public String welcome(String name){
        return "Hello "+name+" , Welcome to "+application +" , time "+System.currentTimeMillis();
    }

    @Cacheable(cacheNames = {CacheName.Redis.TTL_600} , key = "'welcome'+#place+'—'+#name", cacheManager = CacheDrive.REDIS)
    public String welcome(String place , String name){
        return "Hello "+name+" , Welcome to "+place +" , time "+System.currentTimeMillis();
    }

    @Cacheable(cacheNames = {CacheName.Redis.TTL_180} , keyGenerator = CacheKeyGeneratorConfig.MY_KEY_GENERATOR , cacheManager = CacheDrive.REDIS)
    public String welcome(String place , String name , Long timeMs){
        return "Hello "+name+" , Welcome to "+place +" , time "+timeMs;
    }



}
