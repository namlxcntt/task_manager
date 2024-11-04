package com.lxn.task_manager.taskList.services;

import com.lxn.task_manager.core.ApiOutput;
import com.lxn.task_manager.taskList.model.TaskListModel;

import java.util.List;

/**
 * Service interface for managing task lists.
 */
public interface TaskListServices {

    /**
     * Retrieves a task list by its ID.
     *
     * @param id the ID of the task list to retrieve
     * @return the task list model corresponding to the given ID
     */
    ApiOutput<TaskListModel> getTaskListByID(Long id);

    /**
     * Retrieves all task lists.
     *
     * @return a list of all task list models
     */
    ApiOutput<List<TaskListModel>> getAllTaskLists();

    /**
     * Creates a new task list.
     *
     * @param taskListModel the task list model to create
     * @return the created task list model
     */
    ApiOutput<TaskListModel> createTaskList(TaskListModel taskListModel);

    /**
     * Updates an existing task list.
     *
     * @param id the ID of the task list to update
     * @param taskListModel the task list model with updated information
     * @return the updated task list model
     */
    ApiOutput<TaskListModel>  updateTaskList(Long id, TaskListModel taskListModel);

    /**
     * Deletes a task list by its ID.
     *
     * @param id the ID of the task list to delete
     */
    ApiOutput<String> deleteTaskList(Long id);
}