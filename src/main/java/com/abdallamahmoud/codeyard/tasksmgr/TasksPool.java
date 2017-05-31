/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard.tasksmgr;

import java.util.List;
import java.util.Vector;

/**
 *
 * @author abdalla
 */
public class TasksPool {

    private int activeTasks;
    private int maximumConcurrentTasks;
    private List<Task> tasksQueue = new Vector<>();

    public synchronized void requestStart(Task task) {
        tasksQueue.add(task);
        task.setTaskListener(new Task.TaskListener() {
            @Override
            public void done() {
                activeTasks--;
                fetchAndExecute();
            }
        });

        fetchAndExecute();
    }

    private synchronized void fetchAndExecute() {
        while (activeTasks < maximumConcurrentTasks && !tasksQueue.isEmpty()) {
            Task task = tasksQueue.remove(0);
            activeTasks++;
            task.start();
        }
    }

    public int getMaximumConcurrentTasks() {
        return maximumConcurrentTasks;
    }

    public void setMaximumConcurrentTasks(int maximumConcurrentTasks) {
        this.maximumConcurrentTasks = maximumConcurrentTasks;
    }

}
