package com.lxn.task_manager.auth.services;

import com.lxn.task_manager.auth.config.JwtUtils;
import com.lxn.task_manager.auth.model.LoginModel;
import com.lxn.task_manager.auth.model.request.AuthRequest;
import com.lxn.task_manager.auth.model.request.RegisterRequest;
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
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtil;

    private final UserDetailsService userDetailsService;

    private final UserServices userServices;

    private final BCryptPasswordEncoder passwordEncoder;

    private final TokenBlacklistService tokenBlacklistService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtil, UserDetailsService userDetailsService, UserServices userServices, BCryptPasswordEncoder passwordEncoder, TokenBlacklistService tokenBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userServices = userServices;
        this.passwordEncoder = passwordEncoder;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public ResponseEntity<ApiOutput<LoginModel>> login(AuthRequest authRequest) {
        final UserModel user = userServices.findByEmail(authRequest.getEmail());
        if (user == null) return ResponseEntity.badRequest().body(ApiOutput.failure("Email Not found"));
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiOutput.failure("Invalid email or password"));
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
        return ResponseEntity.ok(ApiOutput.success(new LoginModel(token, refreshToken)));
    }


    public ResponseEntity<ApiOutput<UserModel>> register(RegisterRequest registerRequest) {
        if (userServices.findByEmail(registerRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().body(ApiOutput.failure("Email already exists"));
        }
        UserModel user = new UserModel();
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserEntity.Role.USER);
        user.setUsername(registerRequest.getUsername());
        UserModel userModel = userServices.createUser(user);
        return ResponseEntity.ok(ApiOutput.success(userModel));
    }

    public ResponseEntity<ApiOutput<LoginModel>> refreshToken(String oldRefreshToken, String currentToken) {
        if (tokenBlacklistService.isTokenBlacklisted(oldRefreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }
        tokenBlacklistService.blacklistToken(currentToken);
        tokenBlacklistService.blacklistToken(oldRefreshToken);

        String email = jwtUtil.extractEmail(oldRefreshToken);
        String token = jwtUtil.generateToken(email);
        String refreshToken = jwtUtil.generateRefreshToken(email);
        LoginModel loginModel = new LoginModel(token, refreshToken);
        return ResponseEntity.ok(ApiOutput.success(loginModel));
    }

    public ResponseEntity<ApiOutput<String>> logout(String token) {
        if(token == null || token.isEmpty()) return ResponseEntity.badRequest().body(ApiOutput.failure("Token is required"));
        if(jwtUtil.validateToken(token)) {
            tokenBlacklistService.blacklistToken(token);
            return ResponseEntity.ok(ApiOutput.success("Logout success"));
        } else {
            return ResponseEntity.badRequest().body(ApiOutput.failure("Invalid token"));
        }
    }


}