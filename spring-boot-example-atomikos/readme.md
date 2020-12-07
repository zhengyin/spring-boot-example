# Spring-boot Atomikos XA 示例


## JPA , XA , Atomikos 介绍

https://www.baeldung.com/java-atomikos

## 数据准备

1. 创建 orders 库 , 导入表
``` 
CREATE TABLE `t_order` (
  `order_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int unsigned NOT NULL,
  `item_id` int unsigned NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单最后修改时间',
  PRIMARY KEY (`order_id`),
  KEY `item_id` (`item_id`)
) ENGINE=InnoDB
```
2. 创建 inventory 库 , 导入表

``` 
CREATE TABLE `t_inventory` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `item_id` int unsigned NOT NULL DEFAULT '0',
  `amount` int unsigned NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB

```
3. 配置数据源

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

## 运行测试

```
    @Test
    public void count(){
        Assert.assertEquals(orderMapper.getList(0,10).size(),9);
        Assert.assertEquals(inventoryMapper.getList(0,10).size(),9);
    }

    @Test
    public void orderOnlyTransaction(){
        Assert.assertThrows(RuntimeException.class, () -> orderService.orderOnlyTransaction(1));
        Assert.assertNotEquals(orderMapper.countByItemId(1),0);
    }

    @Test
    public void inventoryOnlyTransaction() {
        Assert.assertThrows(RuntimeException.class, () -> orderService.inventoryOnlyTransaction(1));
        Assert.assertNotEquals(inventoryMapper.countByItemId(1),0);
    }

    @Test
    public void createOrder() {
        int itemId = 1;
        int orderNum = orderMapper.countByItemId(1);

        orderService.createOrder(itemId);
        Assert.assertEquals(2,inventoryMapper.getAmount(itemId));
        Assert.assertEquals(++orderNum ,orderMapper.countByItemId(itemId));

        orderService.createOrder(itemId);
        Assert.assertEquals(1,inventoryMapper.getAmount(itemId));
        Assert.assertEquals(++orderNum ,orderMapper.countByItemId(itemId));


        orderService.createOrder(itemId);
        Assert.assertEquals(0,inventoryMapper.getAmount(itemId));
        Assert.assertEquals(++orderNum ,orderMapper.countByItemId(itemId));

        Assert.assertThrows(RuntimeException.class, () -> orderService.createOrder(itemId));
        Assert.assertEquals(orderNum,orderMapper.countByItemId(itemId));
    }

```

## 备注 Atomikos 默认配置
``` 
com.atomikos.icatch.oltp_max_retries = 5
com.atomikos.icatch.log_base_dir = ./
com.atomikos.icatch.tm_unique_name = 192.168.102.102.tm
com.atomikos.icatch.default_jta_timeout = 10000
com.atomikos.icatch.serial_jta_transactions = true
com.atomikos.icatch.allow_subtransactions = true
com.atomikos.icatch.automatic_resource_registration = true
java.naming.factory.initial = com.sun.jndi.rmi.registry.RegistryContextFactory
com.atomikos.icatch.log_base_name = tmlog
com.atomikos.icatch.oltp_retry_interval = 10000
java.naming.provider.url = rmi://localhost:1099
com.atomikos.icatch.checkpoint_interval = 500
com.atomikos.icatch.default_max_wait_time_on_shutdown = 9223372036854775807
com.atomikos.icatch.client_demarcation = false
com.atomikos.icatch.forget_orphaned_log_entries_delay = 86400000
com.atomikos.icatch.trust_client_tm = false
com.atomikos.icatch.force_shutdown_on_vm_exit = false
com.atomikos.icatch.rmi_export_class = none
com.atomikos.icatch.enable_logging = true
com.atomikos.icatch.max_timeout = 300000
com.atomikos.icatch.threaded_2pc = false
com.atomikos.icatch.recovery_delay = 10000
com.atomikos.icatch.max_actives = 50
```