package com.example.taskwithspringbatch;


import com.example.taskwithspringbatch.generator.DataWriteToFile;
import com.example.taskwithspringbatch.helper.MessageLineMapper;
import com.example.taskwithspringbatch.model.DO.Message;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.util.Arrays;

@SpringBootApplication
@EnableTask
@EnableBatchProcessing(modular = true)
public class TaskWithSpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskWithSpringBatchApplication.class, args);
    }



    @Bean
    public FlatFileItemReader<Message> jsonMessageReader(){
        FlatFileItemReader<Message> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(new File(DataWriteToFile.FILE)));
        reader.setLineMapper(new MessageLineMapper());
        return reader;
    }

    @Autowired
    private EntityManagerFactory entityManager;

    @Bean
    public JpaItemWriter<Message> messageItemWriter() {
        JpaItemWriter<Message> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManager);
        return writer;
    }



}
