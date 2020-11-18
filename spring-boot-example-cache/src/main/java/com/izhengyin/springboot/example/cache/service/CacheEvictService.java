package com.izhengyin.springboot.example.cache.service;

import com.izhengyin.springboot.cache.annotation.CacheTarget;
import com.izhengyin.springboot.cache.constant.CacheDrive;
import com.izhengyin.springboot.cache.constant.CacheName;
import com.izhengyin.springboot.cache.constant.GeneratorName;
import com.izhengyin.springboot.example.cache.config.CacheKeyGeneratorConfig;
import com.izhengyin.springboot.example.cache.controller.v1.TestController;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;


/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2019-11-07 13:53
 */
@Service
public class CacheEvictService {

    @CacheTarget(TestController.class)
    @CacheEvict(cacheNames = CacheName.Redis.TTL_5 , keyGenerator = GeneratorName.DEFAULT , cacheManager = CacheDrive.REDIS)
    public void hello(String name){}

    @CacheTarget(value = WelcomeService.class, method = "welcome")
    @CacheEvict(cacheNames = CacheName.Redis.TTL_60 , keyGenerator = GeneratorName.DEFAULT , cacheManager = CacheDrive.REDIS)
    public void noMatterName(String name){

    }

    @CacheTarget(value = WelcomeService.class, method = "welcome")
    @CacheEvict(cacheNames = CacheName.Redis.TTL_600 , key = "'welcome'+#place+'—'+#name", cacheManager = CacheDrive.REDIS)
    public void welcome(String place , String name){

    }

    @CacheEvict(cacheNames = CacheName.Redis.TTL_180 , keyGenerator = CacheKeyGeneratorConfig.MY_KEY_GENERATOR , cacheManager = CacheDrive.REDIS)
    public void welcome(String place , String name , Long timeMs){

    }

}
