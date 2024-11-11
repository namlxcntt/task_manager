package com.lxn.task_manager.task.mapper;

import com.lxn.task_manager.task.entity.TaskEntity;
import com.lxn.task_manager.task.model.TaskModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "taskListId", source = "taskList.listId")
    TaskModel toModel(TaskEntity taskEntity);

    @Mapping(target = "taskList.listId", source = "taskListId")
    TaskEntity toEntity(TaskModel taskModel);
}