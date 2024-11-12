package com.lxn.task_manager.task.services;

import com.lxn.task_manager.core.ApiOutput;
import com.lxn.task_manager.task.entity.TaskEntity;
import com.lxn.task_manager.task.mapper.TaskMapper;
import com.lxn.task_manager.task.model.TaskModel;
import com.lxn.task_manager.task.repo.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServicesImpl implements TaskServices {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    public TaskServicesImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public ApiOutput<TaskModel> getTaskByID(Long id) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(id);
        return taskEntity.map(entity -> {
                    TaskModel taskListModel = taskMapper.toModel(entity);
                    return ApiOutput.success(taskListModel);
                })
                .orElseGet(() -> ApiOutput.failure("Task not found."));
    }

    @Override
    public ApiOutput<List<TaskModel>> getAllTasks() {
        List<TaskModel> taskListEntities = taskRepository.findAll().stream().map(taskMapper::toModel)
                .toList();
        return ApiOutput.success(taskListEntities);
    }

    @Override
    public ApiOutput<TaskModel> createTask(TaskModel taskModel) {
        TaskModel saveResponse = taskMapper.toModel(taskRepository.save(taskMapper.toEntity(taskModel)));
        return ApiOutput.success(saveResponse);
    }

    @Override
    public ApiOutput<TaskModel> updateTask(Long id, TaskModel taskModel) {
        Optional<TaskEntity> updateTaskList = taskRepository.findById(id);
        return updateTaskList.map((entity) -> {
            TaskEntity updatedEntity = taskMapper.toEntity(taskModel);
            updatedEntity.setUpdatedAt(LocalDateTime.now());
            TaskModel updatedModel = taskMapper.toModel(taskRepository.save(updatedEntity));
            return ApiOutput.success(updatedModel);
        }).orElseGet(() -> ApiOutput.failure("Task not found."));
    }

    @Override
    public ApiOutput<String> deleteTask(Long id) {
        taskRepository.deleteById(id);
        return ApiOutput.success("Task list deleted.");
    }
}
