package com.izhengyin.springboot.example.atomikos.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-07 16:33
 */
@Data
@ToString
@TableName("t_inventory")
public class Inventory {
    private Integer id;
    private Integer itemId;
    private Integer amount;
    private Date createTime;
    private Date updateTime;
}
