package com.lxn.task_manager.auth.controller;

import com.lxn.task_manager.auth.model.AuthRequest;
import com.lxn.task_manager.auth.services.AuthServices;
import com.lxn.task_manager.core.ApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class AuthController {

    private final AuthServices authServices;

    @Autowired
    public AuthController(AuthServices authServices) {
        this.authServices = authServices;
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
}