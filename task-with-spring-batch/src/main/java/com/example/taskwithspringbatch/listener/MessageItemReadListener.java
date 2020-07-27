package com.example.taskwithspringbatch.listener;

import com.example.taskwithspringbatch.model.DO.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

import java.io.IOException;
import java.io.Writer;

/**
 * @author want
 * @createTime 2020.07.22.22:28
 */
@Slf4j
public class MessageItemReadListener implements ItemReadListener<Message> {

    @Override
    public void beforeRead() {
    }

    @Override
    public void afterRead(Message item) {
    }

    @Override
    public void onReadError(Exception ex) {
        try{
            log.error(String.format("%s%n", ex.getMessage()));
        }catch (Exception e){
            log.error("write log error : {}",e);
        }
    }
}
