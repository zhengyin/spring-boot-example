package com.izhengyin.springboot.example.h2.dao.entity;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-01 14:44
 */

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Blog {
    private Integer id;
    private String title;
    private String content;
}
