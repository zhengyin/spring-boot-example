package com.izhengyin.springboot.example.cache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2019-11-02 15:55
 */
@SpringBootApplication
@EnableCaching
public class Application {
    public static void main(String[] args){
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }
}