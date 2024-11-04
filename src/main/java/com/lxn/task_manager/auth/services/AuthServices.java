package com.lxn.task_manager.auth.services;

import com.lxn.task_manager.auth.config.JwtUtils;
import com.lxn.task_manager.auth.model.AuthModel;
import com.lxn.task_manager.auth.model.AuthRequest;
import com.lxn.task_manager.core.ApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;

    @Autowired
    public AuthServices(AuthenticationManager authenticationManager, JwtUtils jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    public ApiOutput<AuthModel> authenticated(AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("Invalid username or password", ex);
        }
        String token = jwtUtil.createToken(authRequest.getUsername());
        return ApiOutput.success(new AuthModel(token));
    }
}
