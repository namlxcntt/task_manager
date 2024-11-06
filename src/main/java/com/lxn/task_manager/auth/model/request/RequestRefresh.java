package com.lxn.task_manager.auth.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RequestRefresh {
    private String refreshToken;

    public RequestRefresh(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RequestRefresh() {
    }
}
