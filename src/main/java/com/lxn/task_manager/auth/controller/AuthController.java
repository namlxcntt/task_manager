package com.lxn.task_manager.auth.controller;

import com.lxn.task_manager.auth.model.AuthRequest;
import com.lxn.task_manager.auth.services.AuthServices;
import com.lxn.task_manager.core.ApiOutput;
import com.lxn.task_manager.user.entity.UserEntity;
import com.lxn.task_manager.user.model.UserModel;
import com.lxn.task_manager.user.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/public")
public class AuthController {

    private final AuthServices authServices;

    private final UserServices userServices;



    @Autowired
    public AuthController(AuthServices authServices, UserServices userServices, PasswordEncoder passwordEncoder) {
        this.authServices = authServices;
        this.userServices = userServices;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiOutput<?>> login(@RequestBody AuthRequest authRequest) {
        try {
            ApiOutput<?> response = authServices.authenticated(authRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(ApiOutput.failure(e.getMessage(), 401));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiOutput<?>> register(@RequestBody AuthRequest authRequest) {
        UserModel userRequest = new UserModel();
        userRequest.setPasswordHash(authRequest.getPassword());
        userRequest.setUsername(authRequest.getUsername());
        userRequest.setEmail(authRequest.getEmail());
        userRequest.setCreatedAt(LocalDateTime.now());
        userRequest.setRole(UserEntity.Role.USER);
        UserModel userModel = userServices.createUser(userRequest);
        String response = authServices.generatedJWTToken(authRequest);
        if (userModel == null && response == null) {
            return ResponseEntity.status(401).body(ApiOutput.failure("Register Failure", 401));
        }
        return ResponseEntity.ok(ApiOutput.success(response));
    }
}