package com.lxn.task_manager.task.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskModel {
    private Long taskId;
    private Long taskListId;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Priority priority;
    private Status status;
    private Boolean isRecurring;
    private String recurrencePattern;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }
}