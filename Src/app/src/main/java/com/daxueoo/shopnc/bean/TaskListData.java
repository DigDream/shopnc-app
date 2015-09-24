package com.daxueoo.shopnc.bean;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class TaskListData {

    @Expose
    private List<Task> tasks = new ArrayList<Task>();

    /**
     *
     * @return
     * The tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     *
     * @param tasks
     * The tasks
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}