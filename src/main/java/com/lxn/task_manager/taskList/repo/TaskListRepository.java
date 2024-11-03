package com.lxn.task_manager.taskList.repo;

import com.lxn.task_manager.taskList.entity.TaskListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskListRepository extends JpaRepository<TaskListEntity, Long> {
    // Có thể thêm các query method tùy ý
}
