package com.example.taskwithspringbatch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author want
 * @createTime 2020.07.19.9:25
 */
@Configuration
@Slf4j
public class TaskConfiguration extends DefaultTaskConfigurer {



    public TaskConfiguration(@Autowired DataSource dataSource){
        super(dataSource);
        log.info("datasource: {}",dataSource);
    }
}
