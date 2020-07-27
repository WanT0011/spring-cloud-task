package com.example.hellospringcloudtask.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/**
 * @author want
 * @createTime 2020.07.19.9:15
 */
@Slf4j
public class HelloCommandLineRunner implements CommandLineRunner {

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("hello command is running as a task");
    }
}
