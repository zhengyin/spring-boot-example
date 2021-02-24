package com.izhengyin.springbootexample.dataaccess.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021-02-23 17:40
 */
@Component
@Slf4j
public class JdbcExample implements EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void construct() throws Exception{
        //1.加载驱动程序
        Class.forName(environment.getProperty("spring.datasource.driver-class-name"));
        //2.获得数据库的连接
        Connection conn = DriverManager.getConnection(
                environment.getProperty("spring.datasource.url"),
                environment.getProperty("spring.datasource.username"),
                environment.getProperty("spring.datasource.password")
                );
        //3.通过数据库的连接操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from blog limit 10");
        while(rs.next()){
            log.info(rs.getInt("id")+" => "+rs.getString("title"));
        }
        rs.close();
        stmt.close();
        conn.close();
    }
}
