package com.lxn.task_manager.auth.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {
    private String password;
    private String email;

    public AuthRequest() {
    }
}