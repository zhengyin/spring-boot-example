## Sharding-sphere-jdbc 分库分表


### 1. 创建库 order_1 \ order_2 导入下面的表
``` 
DROP TABLE IF EXISTS t_order_0;
CREATE TABLE `t_order_0` (
  `order_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) unsigned NOT NULL,
  `buyer_id` int(11) unsigned NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单最后修改时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';


DROP TABLE IF EXISTS t_order_1;
CREATE TABLE `t_order_1` (
  `order_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) unsigned NOT NULL,
  `buyer_id` int(11) unsigned NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单最后修改时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';


DROP TABLE IF EXISTS t_order_2;
CREATE TABLE `t_order_2` (
  `order_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) unsigned NOT NULL,
  `buyer_id` int(11) unsigned NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单最后修改时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

```

### 2. 修改数据库连接配置

``` 
spring.datasource.order_1.driver-class-name = com.mysql.cj.jdbc.Driver
spring.datasource.order_1.jdbc-url=jdbc:mysql://localhost/order_1
spring.datasource.order_1.username=root
spring.datasource.order_1.password=zhengyin

spring.datasource.order_2.driver-class-name = com.mysql.cj.jdbc.Driver
spring.datasource.order_2.jdbc-url=jdbc:mysql://localhost/order_2
spring.datasource.order_2.username=root
spring.datasource.order_2.password=zhengyin
```

### 3. 运行测试

``` 
    @Test
    public void getCount(){
        Assert.assertEquals(orderService.count(),9);
    }

    @Test
    public void getList(){
        List<Order> orderList = orderService.getList(5,2);
        System.out.println(JSON.toJSONString(orderList));
        Assert.assertEquals(orderList.size(),2);
        Assert.assertEquals(orderList.get(0).getOrderId().intValue(),6);
        Assert.assertEquals(orderList.get(1).getOrderId().intValue(),7);
    }

    @Test
    public void delMulti(){
         orderService.delMulti(Arrays.asList(1,2,3,4));
         Assert.assertFalse(orderService.exists(1));
         Assert.assertFalse(orderService.exists(2));
         Assert.assertFalse(orderService.exists(3));
         Assert.assertFalse(orderService.exists(4));
    }
```

### XA 事务 (待解决)

> 抛出异常后事务没有回滚，怀疑是数据源 Datasource 需要配置未XaDatasource，但是还未找到shardingsphere的如何配置的。

``` 
<!-- 使用 XA 事务时，需要引入此模块 -->
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>shardingsphere-transaction-xa-core</artifactId>
    <version>${shardingsphere.version}</version>
</dependency>
```

``` 
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean transaction(){
        //db 1 , table 1
        orderMapper.del(1,1);

        //db 0 , table 2
        if(orderMapper.del(2,2) > 0){
            //模拟抛出异常
            throw new RuntimeException("Throw Exception!");
        }
        return true;
    }
```