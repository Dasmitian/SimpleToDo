package com.dasmitian.simpletodo;

public class Task {

    private String taskName;
    private String taskCreated;
    private String taskDue;
    private String taskStatus;
    private boolean hasSubtasks;

    public Task(String taskName, String taskCreated, String taskDue, String taskStatus, boolean hasSubtasks) {
        this.taskName = taskName;
        this.taskCreated = taskCreated;
        this.taskDue = taskDue;
        this.taskStatus = taskStatus;
        this.hasSubtasks = hasSubtasks;
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

    public boolean isHasSubtasks() {
        return hasSubtasks;
    }

    public void setHasSubtasks(boolean hasSubtasks) {
        this.hasSubtasks = hasSubtasks;
    }
}
