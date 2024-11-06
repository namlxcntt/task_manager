package com.lxn.task_manager.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
public class LoginModel {
    private String bearToken;
    private String refreshToken;
}
