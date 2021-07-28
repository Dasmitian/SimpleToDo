package com.dasmitian.simpletodo.ui.tasks;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dasmitian.simpletodo.CustomJsonHandler;
import com.dasmitian.simpletodo.SingleTask;
import com.dasmitian.simpletodo.TasksList;
import com.dasmitian.simpletodo.TestTaskClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class TasksViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<SingleTask>> tasksListData = new MutableLiveData<>();

    public TasksViewModel(Application application) throws FileNotFoundException {
        super(application);

        TasksList tasksList = TasksList.getInstance();
        tasksList.loadTasksFromFile(application.getApplicationInfo().dataDir + "/tasks.json");
        tasksListData.setValue(tasksList.getTasksList());
    }

    public LiveData<ArrayList<SingleTask>> getTaskList() {
        return tasksListData;
    }
}