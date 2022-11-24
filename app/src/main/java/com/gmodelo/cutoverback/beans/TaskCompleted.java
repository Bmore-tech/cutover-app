package com.gmodelo.cutoverback.beans;

public class TaskCompleted {

    boolean taskCompleted;
    boolean taskUploaded;
    boolean taskInitiated;

    public TaskCompleted() {
    }

    @Override
    public String toString() {
        return "TaskCompleted{" +
                "taskCompleted=" + taskCompleted +
                ", taskUploaded=" + taskUploaded +
                ", taskInitiated=" + taskInitiated +
                '}';
    }

    public TaskCompleted(boolean taskCompleted, boolean taskUploaded, boolean taskInitiated) {
        this.taskCompleted = taskCompleted;
        this.taskUploaded = taskUploaded;
        this.taskInitiated = taskInitiated;
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    public boolean isTaskUploaded() {
        return taskUploaded;
    }

    public void setTaskUploaded(boolean taskUploaded) {
        this.taskUploaded = taskUploaded;
    }

    public boolean isTaskInitiated() {
        return taskInitiated;
    }

    public void setTaskInitiated(boolean taskInitiated) {
        this.taskInitiated = taskInitiated;
    }
}
