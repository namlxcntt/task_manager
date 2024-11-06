package com.lxn.task_manager.auth.controller;

import com.lxn.task_manager.auth.model.LoginModel;
import com.lxn.task_manager.auth.model.request.AuthRequest;
import com.lxn.task_manager.auth.model.request.RegisterRequest;
import com.lxn.task_manager.auth.model.request.RequestRefresh;
import com.lxn.task_manager.auth.services.AuthService;
import com.lxn.task_manager.core.ApiOutput;
import com.lxn.task_manager.user.model.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiOutput<LoginModel>> login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiOutput<UserModel>> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiOutput<LoginModel>> refresh(@RequestBody RequestRefresh refreshToken, HttpServletRequest httpServletRequest) {
        return authService.refreshToken(refreshToken.getRefreshToken(), httpServletRequest.getHeader("Authorization")
                .substring(7));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiOutput<String>> logout(HttpServletRequest httpServletRequest) {
        final String authorizationHeader = httpServletRequest.getHeader("Authorization")
                .substring(7);
        return authService.logout(authorizationHeader);
    }
}