package com.dasmitian.simpletodo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class TasksList {
    private static TasksList INSTANCE;
    ArrayList<SingleTask> tasksList = new ArrayList<>();

    private TasksList(){

    }

    public static TasksList getInstance(){
        if (INSTANCE == null){
            INSTANCE = new TasksList();
        }
        return INSTANCE;
    }

    public void loadTasksFromFile(String filename) throws FileNotFoundException {
        Gson gson = new GsonBuilder().create();
        SingleTask[] singleTasks = gson.fromJson(new FileReader(filename), SingleTask[].class);
        tasksList.addAll(Arrays.asList(singleTasks));
    }

    public ArrayList<SingleTask> getTasksList(){
        return tasksList;
    }

}
