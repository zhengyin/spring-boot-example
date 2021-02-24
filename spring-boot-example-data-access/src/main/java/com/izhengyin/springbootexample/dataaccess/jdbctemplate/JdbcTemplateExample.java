package com.izhengyin.springbootexample.dataaccess.jdbctemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021-02-24 13:52
 */
@Component
@Slf4j
public class JdbcTemplateExample {
    private final JdbcTemplate jdbcTemplate;
    public JdbcTemplateExample(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @PostConstruct
    public void construct(){
        List<String> result = jdbcTemplate.query("select * from blog limit 10",(row,i) ->
            row.getInt("id")+" => "+row.getString("title")
        );
        result.forEach(log::info);
    }
}
