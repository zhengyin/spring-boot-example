package com.izhengyin.springboot.example.gwa.contollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-18 15:59
 */
public interface ApiController {

    @GetMapping("/hello")
    String hello();

    @GetMapping(value = "/resource/{id}",name = "xxx")
    String id(@PathVariable int id);
}
