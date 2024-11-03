package com.lxn.task_manager.user.services;

import com.lxn.task_manager.user.entity.UserEntity;
import com.lxn.task_manager.user.mapper.UserMapper;
import com.lxn.task_manager.user.model.UserModel;
import com.lxn.task_manager.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServicesImpl  implements  UserServices{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserServicesImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserModel getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toModel(userEntity);
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public UserModel createUser(UserModel userModel) {
        UserEntity userEntity = userMapper.toEntity(userModel);
        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.toModel(savedUser);
    }

    @Override
    public UserModel updateUser(Long id, UserModel userModel) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUsername(userModel.getUsername());
        existingUser.setEmail(userModel.getEmail());
        existingUser.setPasswordHash(userModel.getPasswordHash());
        existingUser.setRole(userModel.getRole());
        UserEntity updatedUser = userRepository.save(existingUser);
        return userMapper.toModel(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
