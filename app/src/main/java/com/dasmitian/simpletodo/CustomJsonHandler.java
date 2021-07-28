package com.dasmitian.simpletodo;

import java.io.IOException;
import java.io.InputStream;

public class CustomJsonHandler {

    public String readJsonFile(InputStream inputStream){
        String jsonString = null;
        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public void writeJson(){

    }
}
