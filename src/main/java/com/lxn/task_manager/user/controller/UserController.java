package com.lxn.task_manager.user.controller;

import com.lxn.task_manager.user.model.UserModel;
import com.lxn.task_manager.user.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServices userService;

    @Autowired
    public UserController(UserServices userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        UserModel user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        UserModel createdUser = userService.createUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel userModel) {
        UserModel updatedUser = userService.updateUser(id, userModel);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
