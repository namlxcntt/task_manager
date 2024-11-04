package com.lxn.task_manager.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxn.task_manager.core.ApiOutput;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ApiOutput<String> apiOutput = ApiOutput.failure("Unauthorized", 401);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiOutput));
    }
}
