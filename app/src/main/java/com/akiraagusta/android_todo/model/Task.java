package com.akiraagusta.android_todo.model;

public class Task {
    private int task_id;
    private String title;
    private String description;
    private String due_date;
    private boolean status;

    public Task(int task_id, String title, String description, String due_date, boolean status) {
        this.task_id = task_id;
        this.title = title;
        this.description = description;
        this.due_date = due_date;
        this.status = status;
    }

    public Task(String title, String description, String due_date) {
        this.title = title;
        this.description = description;
        this.due_date = due_date;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public boolean isStatus() {
        return status;
    }

    public String isStatusString() {
        return  Boolean.toString(status);
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
