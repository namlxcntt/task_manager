package com.lxn.task_manager.repository;

import com.lxn.task_manager.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // Có thể thêm các query method tùy ý
}
