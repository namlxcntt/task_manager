package com.lxn.task_manager.taskList.mapper;

import com.lxn.task_manager.taskList.entity.TaskListEntity;
import com.lxn.task_manager.taskList.model.TaskListModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskListMapper {
    @Mapping(target = "userId", source = "user.userId")
    TaskListModel toModel(TaskListEntity taskListEntity);

    @Mapping(target = "user.userId", source = "userId")
    TaskListEntity toEntity(TaskListModel taskListModel);
}