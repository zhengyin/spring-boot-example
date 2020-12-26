package com.izhengyin.springboot.example.gwa.contollers.impl;

import com.izhengyin.springboot.example.gwa.contollers.ApiController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-18 16:00
 */
@RestController
@RequestMapping("/api")
public class ApiControllerImpl implements ApiController {

    @Override
    public String hello(){
        return "hello";
    }

    @Override
    public String id(@PathVariable int id){
        return "id -> "+id;
    }
}