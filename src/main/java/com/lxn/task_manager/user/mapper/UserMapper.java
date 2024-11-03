package com.lxn.task_manager.user.mapper;

import com.lxn.task_manager.user.entity.UserEntity;
import com.lxn.task_manager.user.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    UserModel toModel(UserEntity userEntity);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    UserEntity toEntity(UserModel userModel);
}