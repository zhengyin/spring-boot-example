package com.izhengyin.springboot.example.gwa.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.AntPathMatcher;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-17 15:33
 */
@SpringBootTest
public class RequestMatchTest {


    @Test
    public void antPathMatcherTest(){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        Assert.assertTrue(antPathMatcher.match("/api/v1/hello/{name}", "/api/v1/hello/xxx"));
        Assert.assertTrue(antPathMatcher.match("/api/v1/hello/{name}", "/api/v1/hello/1"));
        Assert.assertFalse(antPathMatcher.match("/api/v1/hello/{name}", "/api/v1/hello/1/"));
        Assert.assertFalse(antPathMatcher.match("/api/v1/hello/{name}", "/api/v1/hello/"));
        Assert.assertFalse(antPathMatcher.match("/api/v1/hello/{name}", "/api/v1/hello/xxx/aaa"));
    }
}
