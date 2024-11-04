package com.lxn.task_manager.taskList.controller;

import com.lxn.task_manager.core.ApiOutput;
import com.lxn.task_manager.taskList.model.TaskListModel;
import com.lxn.task_manager.taskList.services.TaskListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-lists")
public class TaskListController {
    private final TaskListServices taskListServices;

    @Autowired
    public TaskListController(TaskListServices taskListServices) {
        this.taskListServices = taskListServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiOutput<TaskListModel>> getTaskListById(@PathVariable Long id) {
        ApiOutput<TaskListModel> taskList = taskListServices.getTaskListByID(id);
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiOutput<List<TaskListModel>>> getAllTaskLists() {
        ApiOutput<List<TaskListModel>> taskLists = taskListServices.getAllTaskLists();
        return ResponseEntity.ok(taskLists);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiOutput<TaskListModel>> createTaskList(@RequestBody TaskListModel taskListModel) {
        ApiOutput<TaskListModel> createdTaskList = taskListServices.createTaskList(taskListModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiOutput<TaskListModel>> updateTaskList(@PathVariable Long id, @RequestBody TaskListModel taskListModel) {
        ApiOutput<TaskListModel> updatedTaskList = taskListServices.updateTaskList(id, taskListModel);
        return ResponseEntity.ok(updatedTaskList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiOutput<String>> deleteTaskList(@PathVariable Long id) {
        ApiOutput<String> response = taskListServices.deleteTaskList(id);
        return ResponseEntity.ok(response);
    }
}