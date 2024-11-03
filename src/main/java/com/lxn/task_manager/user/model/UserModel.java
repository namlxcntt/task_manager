package com.lxn.task_manager.user.model;

import com.lxn.task_manager.user.entity.UserEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
public class UserModel {
    private Long userId;
    private String username;
    private String email;
    private String passwordHash;
    private UserEntity.Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserModel(Long userId, String username, String email, String passwordHash, UserEntity.Role role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}