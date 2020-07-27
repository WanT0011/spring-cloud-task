package com.example.taskwithspringbatch.listener;

import com.example.taskwithspringbatch.model.DO.Message;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author want
 * @createTime 2020.07.22.22:28
 */
@Slf4j
public class MessageWriteListener implements ItemWriteListener<Message> {

    private AtomicInteger count = new AtomicInteger();

    @Override
    public void beforeWrite(List<? extends Message> items) {
        int value = count.incrementAndGet();
        log.info("write "+ value +" times");
    }

    @Override
    public void afterWrite(List<? extends Message> items) {
        log.info("after write");
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Message> items) {
        try {
            log.error(String.format("%s%n", exception.getMessage()));
            for (Message message : items) {
                log.error(String.format("Failed writing message id: %s", message.getObjectId()));
            }
        } catch (Exception e) {
            log.error("write log error : {}",e);
        }
    }
}
