package com.izhengyin.springboot.example.atomikos.config;
import com.izhengyin.springboot.example.atomikos.config.factory.XaDatasourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import javax.sql.XADataSource;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2020-12-05 12:24
 */
@Configuration
@MapperScan(value ="com.izhengyin.springboot.example.atomikos.dao.mapper.inventory" ,sqlSessionFactoryRef = "InventorySqlSessionFactory")
public class InventoryXADatasourceConfiguration implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Bean(name = "InventoryXADatasource")
    public DataSource masterDataSource() {
        XADataSource dataSource = XaDatasourceFactory.createDatasource(environment,"spring.datasource.inventory");
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(dataSource);
        atomikosDataSourceBean.setUniqueResourceName("InventoryXADatasource");
        atomikosDataSourceBean.setPoolSize(5);
        return atomikosDataSourceBean;
    }

    @Bean(name = "InventorySqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("InventoryXADatasource") DataSource orderDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(orderDataSource);
        return sessionFactory.getObject();
    }




}
