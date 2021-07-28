package com.dasmitian.simpletodo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;

public class SingleSubTask {

    private String subTaskName;
    private String subTaskCreated;
    private String subTaskDue;
    private String subTaskStatus;

    public SingleSubTask(){

    }

    public String getSubTaskName() {
        return subTaskName;
    }

    public void setSubTaskName(String subTaskName) {
        this.subTaskName = subTaskName;
    }

    public String getSubTaskCreated() {
        return subTaskCreated;
    }

    public void setSubTaskCreated(String subTaskCreated) {
        this.subTaskCreated = subTaskCreated;
    }

    public String getSubTaskDue() {
        return subTaskDue;
    }

    public void setSubTaskDue(String subTaskDue) {
        this.subTaskDue = subTaskDue;
    }

    public String getSubTaskStatus() {
        return subTaskStatus;
    }

    public void setSubTaskStatus(String subTaskCompleted) {
        this.subTaskStatus = subTaskCompleted;
    }
}
