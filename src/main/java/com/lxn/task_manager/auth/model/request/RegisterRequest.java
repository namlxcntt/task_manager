package com.lxn.task_manager.auth.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
}
