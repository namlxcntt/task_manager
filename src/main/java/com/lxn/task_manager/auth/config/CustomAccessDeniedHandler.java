package com.lxn.task_manager.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxn.task_manager.core.ApiOutput;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ApiOutput<String> apiOutput = ApiOutput.failure("Unauthorized", 403);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiOutput));
    }
}