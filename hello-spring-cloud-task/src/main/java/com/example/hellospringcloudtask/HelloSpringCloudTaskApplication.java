package com.example.hellospringcloudtask;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import com.example.hellospringcloudtask.runner.HelloCommandLineRunner;

@SpringBootApplication
@EnableTask
public class HelloSpringCloudTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringCloudTaskApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(){
        return new HelloCommandLineRunner();
    }
}
