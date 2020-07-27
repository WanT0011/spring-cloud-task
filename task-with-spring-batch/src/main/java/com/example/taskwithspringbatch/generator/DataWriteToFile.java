package com.example.taskwithspringbatch.generator;

import com.example.taskwithspringbatch.model.DO.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.micrometer.core.instrument.util.JsonUtils;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.codehaus.jettison.json.JSONObject;

import java.io.*;
import java.util.UUID;

/**
 * @author want
 * @createTime 2020.07.22.23:01
 */
public class DataWriteToFile {
    public static final String FILE = "data";


    public static void main(String[] args) {
        File file = new File(FILE);
        try(
            Writer fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw)
        ){
            ObjectMapper objectMapper = new ObjectMapper();
            for (int i = 0; i < 100; i++) {
                Message message = new Message();
                message.setObjectId(i+1+"");
                message.setContent(UUID.randomUUID().toString());
                String value = objectMapper.writeValueAsString(message);
                System.out.println("value = " + value);
                bw.write(value);
                bw.newLine();
            }
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
