package com.izhengyin.springboot.example.cache.test;
import com.izhengyin.springboot.cache.constant.RedisInstance;
import com.izhengyin.springboot.example.cache.Application;
import com.izhengyin.springboot.example.cache.service.CacheEvictService;
import com.izhengyin.springboot.example.cache.service.WelcomeService;
import com.izhengyin.springboot.example.cache.task.CacheEvictTask;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2019-11-07 16:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAutoConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestPropertySource(locations = {"classpath:application.properties"})
public class ApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WelcomeService welcomeService;

    @Autowired
    private CacheEvictService cacheEvictService;

    @Autowired
    private @Qualifier(RedisInstance.REDIS_CACHE_TEMPLATE) StringRedisTemplate cacheRedisTemplate;


    /**
     * controller 缓存测试
     */
    @Test
    public void controllerCacheTest() throws InterruptedException{
        String name = "visitor";
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/hello/{name}",String.class,name);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        /**
         * 第一次请求的内容
         */
        String content = responseEntity.getBody();
        /**
         * 下次请求缓存命中，值相等
         */
        Assert.assertEquals(content,testRestTemplate.getForEntity("/hello/{name}",String.class,name).getBody());
        /**
         * 缓存设置的是5秒，让缓存自动过期
         */
        TimeUnit.SECONDS.sleep(6);
        /**
         * 缓存过期后值不相等
         */
        Assert.assertNotEquals(content,testRestTemplate.getForEntity("/hello/{name}",String.class,name).getBody());
        /**
         * 获取一个新的值
         */
        String newContent = testRestTemplate.getForEntity("/hello/{name}",String.class,name).getBody();
        Assert.assertEquals(newContent,testRestTemplate.getForEntity("/hello/{name}",String.class,name).getBody());
        /**
         * 删除缓存
         */
        testRestTemplate.put("/helloCacheEvict/{name}",null,name);
        /**
         * 缓存已失效，值不相等
         */
        Assert.assertNotEquals(newContent,testRestTemplate.getForEntity("/hello/{name}",String.class,name).getBody());
    }

    /**
     * service 缓存测试
     * @throws InterruptedException
     */
    @Test
    public void serviceCacheTest()  throws InterruptedException{
        /**
         * 定时任务 CacheEvictTask 在5秒后会清除缓存 ， 在此期间缓存未失效，值相等
         */
        String content = welcomeService.welcome(CacheEvictTask.NAME);
        Assert.assertEquals(content,welcomeService.welcome(CacheEvictTask.NAME));
        TimeUnit.SECONDS.sleep(6);
        /**
         * 6秒后，缓存已经被删除，值不相等
         */
        Assert.assertNotEquals(content,welcomeService.welcome(CacheEvictTask.NAME));
    }

    /**
     * 自定义key，测试
     * @throws InterruptedException
     */
    @Test
    public void customKeyTest()  throws InterruptedException{
        String place = "孔夫子";
        String name = "zhengyin";
        String content = welcomeService.welcome(place,name);
        Assert.assertEquals(content,welcomeService.welcome(place,name));
        cacheEvictService.welcome(place,name);
        Assert.assertNotEquals(content,welcomeService.welcome(place,name));
    }


    /**
     * 自定义key生成器，测试
     * @throws InterruptedException
     */
    @Test
    public void customKeyGeneratorTest()  throws InterruptedException{
        String place = "beijing";
        String name = "zhengyin";
        String content = welcomeService.welcome(place,name,System.currentTimeMillis());
        TimeUnit.SECONDS.sleep(1);
        /**
         * 自动以的key只使用了，第一和第二个参数作为缓存的key，因此即使时间参数发生变化，同样命中cache，值相等
         */
        Assert.assertEquals(content,welcomeService.welcome(place,name,System.currentTimeMillis()));
        /**
         * 清除缓存后值不相等
         */
        cacheEvictService.welcome(place,name,System.currentTimeMillis());
        Assert.assertNotEquals(content,welcomeService.welcome(place,name));
    }


    @Test
    public void directUseRedisTest(){
        cacheRedisTemplate.opsForValue().set("a","1");
        Assert.assertEquals("1",cacheRedisTemplate.opsForValue().get("a"));
    }

}
