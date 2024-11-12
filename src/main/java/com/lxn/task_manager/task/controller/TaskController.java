package com.lxn.task_manager.task.controller;

import com.lxn.task_manager.core.ApiOutput;
import com.lxn.task_manager.task.model.TaskModel;
import com.lxn.task_manager.task.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskServices taskServices;

    @Autowired
    public TaskController(TaskServices taskServices) {
        this.taskServices = taskServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiOutput<TaskModel>> getTaskById(@PathVariable Long id) {
        ApiOutput<TaskModel> task = taskServices.getTaskByID(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiOutput<List<TaskModel>>> getAllTasks() {
        ApiOutput<List<TaskModel>> tasks = taskServices.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiOutput<TaskModel>> createTask(@RequestBody TaskModel taskModel) {
        ApiOutput<TaskModel> createdTask = taskServices.createTask(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiOutput<TaskModel>> updateTask(@PathVariable Long id, @RequestBody TaskModel taskModel) {
        ApiOutput<TaskModel> updatedTask = taskServices.updateTask(id, taskModel);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiOutput<String>> deleteTask(@PathVariable Long id) {
        ApiOutput<String> response = taskServices.deleteTask(id);
        return ResponseEntity.ok(response);
    }
}