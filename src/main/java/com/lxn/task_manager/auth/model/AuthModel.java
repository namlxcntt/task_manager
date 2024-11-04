package com.lxn.task_manager.auth.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class AuthModel {
    private String token;

    public AuthModel(String token) {
        this.token = token;
    }
}
