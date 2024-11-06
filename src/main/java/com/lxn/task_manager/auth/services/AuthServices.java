package com.lxn.task_manager.auth.services;

import com.lxn.task_manager.auth.config.JwtUtils;
import com.lxn.task_manager.auth.model.AuthModel;
import com.lxn.task_manager.auth.model.AuthRequest;
import com.lxn.task_manager.core.ApiOutput;
import com.lxn.task_manager.user.entity.UserEntity;
import com.lxn.task_manager.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    public AuthServices(AuthenticationManager authenticationManager, JwtUtils jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public ApiOutput<AuthModel> authenticated(AuthRequest authRequest) throws Exception {
        try {
           final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("Invalid email or password", ex);
        }
        String token = jwtUtil.generateToken(authRequest.getEmail());
        return ApiOutput.success(new AuthModel(token));
    }

    public String generatedJWTToken(AuthRequest authRequest) {
        return jwtUtil.generateToken(authRequest.getEmail());
    }

    public ApiOutput<UserEntity> verifyJwtAndUser(String token, String email) {
        if (!jwtUtil.validateToken(token,email)) {
            return ApiOutput.failure("Invalid JWT token", 401);
        }
        String emailExtract = jwtUtil.extractEmail(token);
        UserEntity user = userRepository.findByEmail(emailExtract)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + emailExtract));

        return ApiOutput.success(user);
    }
}