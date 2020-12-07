package com.izhengyin.springboot.example.atomikos.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author zhengyin
 */
@Data
@ToString
@TableName("t_order")
public class Order {
    private Integer orderId;
    private Integer sellerId;
    private Integer itemId;
    private Date createTime;
    private Date updateTime;
}
