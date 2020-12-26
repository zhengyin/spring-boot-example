package com.izhengyin.springboot.example.ssjdbc.config.factory;

import com.mysql.cj.jdbc.MysqlXADataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import javax.sql.XADataSource;
import java.util.Objects;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2019-05-29 18:06
 */
@Slf4j
public class XaDatasourceFactory {

    /**
     * 创建一个数据源
     * @param environment
     * @param dbName
     * return
     */
    public static XADataSource createDatasource(Environment environment , final String dbName){
        final String driverClassName = environment.getProperty(dbName + ".driver-class-name");
        final String url = environment.getProperty(dbName + ".jdbc-url");
        final String username = environment.getProperty(dbName + ".username");
        final String password = environment.getProperty(dbName + ".password");
        Objects.requireNonNull(driverClassName);
        Objects.requireNonNull(url);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        MysqlXADataSource dataSource = new MysqlXADataSource();
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
