package com.lxn.task_manager.task.repo;

import com.lxn.task_manager.task.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
