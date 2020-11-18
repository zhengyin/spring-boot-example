package com.izhengyin.springboot.example.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2019-11-08 09:33
 */
@Configuration
public class CacheKeyGeneratorConfig {

    @Value("${spring.application.name}")
    private String application;
    public static final String MY_KEY_GENERATOR = "MY_KEY_GENERATOR";

    /**
     * 自定义key生成器
     * @return
     */
    @Bean(MY_KEY_GENERATOR)
    public KeyGenerator myKeyGenerator(){
        return (target,method,params) -> {
            StringBuilder builder = new StringBuilder();
            builder.append(application)
                    .append(":")
                    .append("MY_CACHE_KEY")
                    .append(":")
                    .append(method.getName())
                    .append(":")
                    .append(params[0])
                    .append("-")
                    .append(params[1]);
            return builder.toString();
        };
    }
}
