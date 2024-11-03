package com.lxn.task_manager.user.repo;

import com.lxn.task_manager.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
