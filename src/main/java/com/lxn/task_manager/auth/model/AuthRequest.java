package com.lxn.task_manager.auth.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthRequest {
    private String username;
    private String password;
    private String mobileKey;
    private String email;

    public AuthRequest(String username, String password, String mobileKey, String email) {
        this.username = username;
        this.password = password;
        this.mobileKey = mobileKey;
        this.email = email;
    }

    public AuthRequest() {
    }
}