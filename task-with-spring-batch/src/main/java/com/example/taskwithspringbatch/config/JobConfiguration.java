package com.example.taskwithspringbatch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author want
 * @createTime 2020.07.19.9:31
 */
@Configuration
@Slf4j
public class JobConfiguration {
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public JobParametersBuilder jobParametersBuilder(){
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addDate("date",new Date());
        return builder;
    }

    @Bean
    public Job firstJob(){
        return jobBuilderFactory.get("firstJob")
                .incrementer(new RunIdIncrementer())
                .start(stepBuilderFactory
                            .get("firstJob-firstStep")
                            .tasklet((stepContribution,chunkContext)->{
                                System.out.println("firstJob-firstStep");
                                return RepeatStatus.FINISHED;
                        }).build()
                )
                .next(stepBuilderFactory
                            .get("firstJob-nextStep")
                            .tasklet((stepContribution,chunkContext)->{
                                System.out.println("firstJob-nextStep");
                                return RepeatStatus.FINISHED;
                        }).build()
                )
                .build();
    }
}
