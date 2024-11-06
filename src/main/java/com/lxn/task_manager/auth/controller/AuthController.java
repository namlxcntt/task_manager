package com.lxn.task_manager.auth.controller;

import com.lxn.task_manager.auth.config.JwtUtils;
import com.lxn.task_manager.auth.model.AuthRequest;
import com.lxn.task_manager.auth.model.RegisterRequest;
import com.lxn.task_manager.core.ApiOutput;
import com.lxn.task_manager.user.entity.UserEntity;
import com.lxn.task_manager.user.model.UserModel;
import com.lxn.task_manager.user.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtil;

    private final UserDetailsService userDetailsService;

    private final UserServices userServices;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtil, UserDetailsService userDetailsService, UserServices userServices, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userServices = userServices;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        final UserModel user = userServices.findByEmail(authRequest.getEmail());
        if (user == null) return ResponseEntity.badRequest().body("Email Not found");
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiOutput<UserModel>> register(@RequestBody RegisterRequest registerRequest) {
        if (userServices.findByEmail(registerRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().body(ApiOutput.failure("Email already exists"));
        }
        UserModel user = new UserModel();
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserEntity.Role.USER);
        user.setUsername(registerRequest.getEmail());
        UserModel userModel = userServices.createUser(user);
        return ResponseEntity.ok(ApiOutput.success(userModel));
    }

}