package com.izhengyin.springbootexample.dataaccess.entity;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-01 14:44
 */
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    private Integer id;
    private String title;
    private String content;
}
