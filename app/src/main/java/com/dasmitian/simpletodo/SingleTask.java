package com.dasmitian.simpletodo;

import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;

import org.json.*;

import java.util.ArrayList;

public class SingleTask {

    private String taskName;
    private String taskCreated;
    private String taskDue;
    private String taskStatus;
    private ArrayList<SingleSubTask> subtaskList;


    public SingleTask() {
        // Required empty public constructor
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskCreated() {
        return taskCreated;
    }

    public void setTaskCreated(String taskCreated) {
        this.taskCreated = taskCreated;
    }

    public String getTaskDue() {
        return taskDue;
    }

    public void setTaskDue(String taskDue) {
        this.taskDue = taskDue;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public ArrayList<SingleSubTask> getSubtaskList() {
        return subtaskList;
    }

    public void setSubtaskList(ArrayList<SingleSubTask> subtaskList) {
        this.subtaskList = subtaskList;
    }

    public int getCompletedSubtasksCount(){
        return countCompletedSubtasks();
    }

    private int countCompletedSubtasks(){
        int completedSubtasks = 0;

        for(SingleSubTask subTask : subtaskList){
            if(subTask.getSubTaskStatus().equals("Completed")){
                completedSubtasks++;
            }
        }

        return completedSubtasks;
    }
}