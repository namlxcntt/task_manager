package com.lxn.task_manager.auth.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class AuthModel {
    private String bearToken;

    public AuthModel(String bearToken) {
        this.bearToken = bearToken;
    }
}
