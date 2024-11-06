package com.lxn.task_manager.auth.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthRequest {
    private String password;
    private String email;

    public AuthRequest(String username, String password, String mobileKey, String email) {
        this.password = password;
        this.email = email;
    }

    public AuthRequest() {
    }
}