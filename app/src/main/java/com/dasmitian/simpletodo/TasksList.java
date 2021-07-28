package com.dasmitian.simpletodo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;

public class TasksList {
    private static TasksList INSTANCE;
    private static ArrayList<SingleTask> tasksList = new ArrayList<>();

    private TasksList(){

    }

    public static TasksList getInstance(String filename){
        if (INSTANCE == null){
            INSTANCE = new TasksList();
            try {
                loadTasksFromFile(filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }

    public static TasksList getInstance(){
        if (INSTANCE == null){
            INSTANCE = new TasksList();
        }
        return INSTANCE;
    }

    private static void loadTasksFromFile(String filename) throws FileNotFoundException {
        Gson gson = new GsonBuilder().create();
        SingleTask[] singleTasks = gson.fromJson(new FileReader(filename), SingleTask[].class);
        tasksList.addAll(Arrays.asList(singleTasks));
    }

    public ArrayList<SingleTask> getTasksList(){
        return tasksList;
    }

    public void writeToJson(String filename) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter(filename);
        gson.toJson(tasksList, writer);
        writer.flush();
        writer.close();
    }

}
