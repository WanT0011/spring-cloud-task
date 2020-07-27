package com.example.taskwithspringbatch.helper;

import com.example.taskwithspringbatch.model.DO.Message;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import org.springframework.batch.item.file.LineMapper;

import java.util.Map;

/**
 * @author want
 * @createTime 2020.07.22.22:20
 */
public class MessageLineMapper implements LineMapper<Message> {
    private MappingJsonFactory factory = new MappingJsonFactory();

    @Override
    public Message mapLine(String line, int lineNumber) throws Exception {
        JsonParser parser = factory.createParser(line);
        Message message = parser.readValueAs(Message.class);

        return message;
    }
}
