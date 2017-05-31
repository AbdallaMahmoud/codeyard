/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdallamahmoud.codeyard.config;

import com.abdallamahmoud.codeyard.tasksmgr.TasksPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author abdalla
 */
@Configuration
public class TaskManagerConfig {

    @Value("${coderun.maxconcurrent}")
    private int maximumConcurrentTasks;

    @Bean
    public TasksPool tasksPool() {
        TasksPool tasksPool = new TasksPool();
        tasksPool.setMaximumConcurrentTasks(maximumConcurrentTasks);
        return tasksPool;
    }
}
