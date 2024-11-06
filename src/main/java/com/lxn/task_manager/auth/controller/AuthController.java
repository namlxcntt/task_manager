package com.lxn.task_manager.auth.controller;

import com.lxn.task_manager.auth.config.JwtUtils;
import com.lxn.task_manager.auth.model.AuthRequest;
import com.lxn.task_manager.auth.model.RegisterRequest;
import com.lxn.task_manager.auth.services.AuthServices;
import com.lxn.task_manager.core.ApiOutput;
import com.lxn.task_manager.user.entity.UserEntity;
import com.lxn.task_manager.user.model.UserModel;
import com.lxn.task_manager.user.repo.UserRepository;
import com.lxn.task_manager.user.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/public")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtil;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtil, UserDetailsService userDetailsService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        final Optional<UserEntity> user  = userRepository.findByEmail(authRequest.getEmail());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Email Not found");
        }
        if (!passwordEncoder.matches(authRequest.getPassword(), user.get().getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserEntity.Role.USER);
        user.setUsername(registerRequest.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

}