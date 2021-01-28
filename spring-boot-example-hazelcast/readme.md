# Spring Boot 使用 Hazelcast 示例

## 运行步骤

1. 进入到项目目录，以不同端口启动服务

```  
 mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=8080"
 mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=8081"
 
 看到如下输出，服务启动完成
 Members {size:2, ver:2} [
         Member [your ip]:5701 - a23354c8-fa5f-4408-b280-47fe53b314ac this
         Member [your ip]:5702 - 46fa356c-0cb3-4d2f-b96a-5e58a0357251
 ]
```

2. 测试在一个服务存储数据在另一个服务读取数据

``` 
➜  spring-boot-example git:(master) ✗ curl --data "key=key1&value=hazelcast" "http://localhost:8080/put"
➜  spring-boot-example git:(master) ✗ curl "http://localhost:8081/get?key=key1"

```

3. 测试引用对象的修改

> 

``` 
curl -X POST http://127.0.0.1:8080/student/a
curl http://127.0.0.1:8081/students  
curl -X POST  http://127.0.0.1:8080/mockConcurrentUpdate/a/course/c1 &
curl -X POST  http://127.0.0.1:8080/mockConcurrentUpdate/a/course/c2 &
curl http://127.0.0.1:8081/students  
```

## 参考

* https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-hazelcast
* https://guides.hazelcast.org/hazelcast-embedded-springboot/
* https://github.com/hazelcast-guides/hazelcast-embedded-springboot/blob/master/src/main/resources/hazelcast.yaml


