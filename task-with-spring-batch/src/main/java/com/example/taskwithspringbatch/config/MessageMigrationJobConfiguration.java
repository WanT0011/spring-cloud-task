package com.example.taskwithspringbatch.config;

import com.example.taskwithspringbatch.helper.MessageLineMapper;
import com.example.taskwithspringbatch.listener.MessageItemReadListener;
import com.example.taskwithspringbatch.listener.MessageWriteListener;
import com.example.taskwithspringbatch.model.DO.Message;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.io.File;
import java.io.Writer;
import java.util.Map;

/**
 * @author want
 * @createTime 2020.07.22.21:49
 */
@Configuration
public class MessageMigrationJobConfiguration {
    @Resource
    JobBuilderFactory jobBuilderFactory;
    @Resource
    StepBuilderFactory stepBuilderFactory;

    private static final int CHUNK_SIZE = 10;
//Spring Batch提供了skip的机制，也就是说，如果出错了，可以跳过。如果你不设置skip，那么一条数据出错了，整个job都会挂掉。
//设置skip的时候一定要设置什么Exception才需要跳过，并且跳过多少条数据。如果失败的数据超过你设置的skip limit，那么job就会失败。
    private static final int SKIP_LIMIT = 10;

    private static final int RETRY_COUNT = 3;

    @Resource
    FlatFileItemReader<Message> jsonMessageReader;

    @Resource
    JpaItemWriter<Message> messageItemWriter;




    @Bean
    public Job messageMigrationJob(){
        return jobBuilderFactory.get("messageMigrationJob")
                .start(messageMigrationStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step messageMigrationStep(){
        return stepBuilderFactory.get("messageMigrationStep")
                //10次每写入
                .<Message,Message>chunk(CHUNK_SIZE)
                .reader(jsonMessageReader).faultTolerant().retryLimit(RETRY_COUNT).retry(Exception.class).skip(JsonParseException.class).skipLimit(SKIP_LIMIT)
                .listener(new MessageItemReadListener())
                .writer(messageItemWriter).faultTolerant().retryLimit(RETRY_COUNT).retry(Exception.class).skip(Exception.class).skipLimit(SKIP_LIMIT)
                .listener(new MessageWriteListener())

                //从它的接口可以看出，需要定义输入和输出的类型，把输入I通过某些逻辑处理之后，返回输出O。
//                .processor()
                .build();
    }

//    Spring Batch给我们提供了很多好用实用的reader，基本能满足我们所有需求。
//    比如FlatFileItemReader，JdbcCursorItemReader，JpaPagingItemReader等。也可以自己实现Reader。





}
