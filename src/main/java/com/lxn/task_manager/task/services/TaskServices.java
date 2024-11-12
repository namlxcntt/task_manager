package com.lxn.task_manager.task.services;

import com.lxn.task_manager.core.ApiOutput;
import com.lxn.task_manager.task.model.TaskModel;

import java.util.List;

/**
 * Service interface for managing tasks.
 */
public interface TaskServices {

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return the task model corresponding to the given ID
     */
    ApiOutput<TaskModel> getTaskByID(Long id);

    /**
     * Retrieves all tasks.
     *
     * @return a list of all task models
     */
    ApiOutput<List<TaskModel>> getAllTasks();

    /**
     * Creates a new task.
     *
     * @param taskModel the task model to create
     * @return the created task model
     */
    ApiOutput<TaskModel> createTask(TaskModel taskModel);

    /**
     * Updates an existing task.
     *
     * @param id the ID of the task to update
     * @param taskModel the task model with updated information
     * @return the updated task model
     */
    ApiOutput<TaskModel> updateTask(Long id, TaskModel taskModel);

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     */
    ApiOutput<String> deleteTask(Long id);
}