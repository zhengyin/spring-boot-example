package com.izhengyin.springboot.example.ssjdbc.config.factory;

import com.alibaba.fastjson.JSON;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2019-05-29 18:06
 */
@Slf4j
public class DatasourceFactory {

    /**
     * 创建一个数据源
     * @param environment
     * @param dbName
     * return
     */
    public static DataSource createDatasource(Environment environment , final String dbName){


        final String driverClassName = environment.getProperty(dbName + ".driver-class-name");
        final String url = environment.getProperty(dbName + ".jdbc-url");
        final String username = environment.getProperty(dbName + ".username");
        final String password = environment.getProperty(dbName + ".password");
        final int maxActive = Optional.ofNullable(environment.getProperty(dbName + ".maximum-pool-size")).map(Integer::parseInt).orElse(10);
        final int minIdle = Optional.ofNullable(environment.getProperty(dbName + ".minimum-idle")).map(Integer::parseInt).orElse(10);
        final long idleTimeout = Optional.ofNullable(environment.getProperty(dbName + ".idle-timeout")).map(Long::parseLong).orElse(60000L);
        final long maxLifeTime = Optional.ofNullable(environment.getProperty(dbName + ".max-life-time")).map(Long::parseLong).orElse(110000L);

        Objects.requireNonNull(driverClassName);
        Objects.requireNonNull(url);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(maxActive);
        config.setMinimumIdle(minIdle);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifeTime);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource dataSource = new HikariDataSource(config);
        log.info(dbName+" Instanced {} , driverClassName {} , url {}  , maxActive {} , minIdle {} " ,
                dataSource.toString(),driverClassName,url,maxActive,minIdle
        );
        log.info("HikariConfig ["+dbName+"] -> "+ JSON.toJSONString(config).replace(password,"***").replace(username,"***"));
        return dataSource;
    }
}
