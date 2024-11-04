package com.lxn.task_manager.taskList.services;

import com.lxn.task_manager.core.ApiOutput;
import com.lxn.task_manager.taskList.entity.TaskListEntity;
import com.lxn.task_manager.taskList.mapper.TaskListMapper;
import com.lxn.task_manager.taskList.model.TaskListModel;
import com.lxn.task_manager.taskList.repo.TaskListRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskListServicesImpl implements TaskListServices {
    private final TaskListRepository taskListRepository;
    private final TaskListMapper taskListMapper;

    public TaskListServicesImpl(TaskListRepository taskRepository, TaskListMapper taskListMapper) {
        this.taskListRepository = taskRepository;
        this.taskListMapper = taskListMapper;
    }

    @Override
    public ApiOutput<TaskListModel> getTaskListByID(Long id) {
        Optional<TaskListEntity> taskListEntity = taskListRepository.findById(id);
        return taskListEntity.map(entity -> {
                    TaskListModel taskListModel = taskListMapper.toModel(entity);
                    return ApiOutput.success(taskListModel);
                })
                .orElseGet(() -> ApiOutput.failure("Task list not found."));
    }

    @Override
    public ApiOutput<List<TaskListModel>> getAllTaskLists() {
        List<TaskListModel> taskListEntities = taskListRepository.findAll().stream().map(taskListMapper::toModel)
                .toList();
        return ApiOutput.success(taskListEntities);
    }

    @Override
    public ApiOutput<TaskListModel> createTaskList(TaskListModel taskListModel) {
        TaskListModel saveResponse = taskListMapper.toModel(taskListRepository.save(taskListMapper.toEntity(taskListModel)));
        return ApiOutput.success(saveResponse);
    }

    @Override
    public ApiOutput<TaskListModel> updateTaskList(Long id, TaskListModel taskListModel) {
        Optional<TaskListEntity> updateTaskList = taskListRepository.findById(id);
        return updateTaskList.map((entity) -> {
            TaskListEntity updatedEntity = taskListMapper.toEntity(taskListModel);
            updatedEntity.setUpdatedAt(LocalDateTime.now());
            TaskListModel updatedModel = taskListMapper.toModel(taskListRepository.save(updatedEntity));
            return ApiOutput.success(updatedModel);
        }).orElseGet(() -> ApiOutput.failure("Task list not found."));
    }

    @Override
    public ApiOutput<String> deleteTaskList(Long id) {
        taskListRepository.deleteById(id);
        return ApiOutput.success("Task list deleted.");
    }


}
