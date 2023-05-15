package com.example.partial;

public class AddTasks {
    String userID;
    String taskName;
    String taskDesc;
    String taskDue;
    String taskPriority;
    String taskStatus;

    public AddTasks() {
    }

    public AddTasks(String userID, String taskName, String taskDesc, String taskDue, String taskPriority, String taskStatus) {
        this.userID = userID;
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.taskDue = taskDue;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskDue() {
        return taskDue;
    }

    public void setTaskDue(String taskDue) {
        this.taskDue = taskDue;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
