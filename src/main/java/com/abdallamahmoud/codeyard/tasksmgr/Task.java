/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard.tasksmgr;

/**
 *
 * @author abdalla
 */
public abstract class Task extends Thread {

    private TaskListener taskListener;

    public TaskListener getTaskListener() {
        return taskListener;
    }

    public void setTaskListener(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    public void reportDone() {
        taskListener.done();
    }

    public static interface TaskListener {

        void done();
    }

}
