package com.lxn.task_manager.core;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ApiOutput<T> {
    /**
     * Status code for api output
     * Success : 0
     * Failure : 1
     */
    private Integer status;

    /**
     * Data for api output
     * Success return data
     * Failure return message cause error
     */
    private T data;

    /**
     * Error message for api output
     */
    private String errorMessage;

    private String message;

    public ApiOutput() {
        this.data = null;
        this.status = 0;
        this.errorMessage = null;
    }

    public ApiOutput(Integer status, T data, String errorMessage, String message) {
        this.status = status;
        this.data = data;
        this.errorMessage = errorMessage;
        this.message = message;
    }


    public static <T> ApiOutput<T> success(T data) {
        return new ApiOutput<>(0, data, null,"Success");
    }

    public static <T> ApiOutput<T> failure(String errorMessage) {
        return new ApiOutput<>(0, null, errorMessage,null);
    }
    public static <T> ApiOutput<T> failure(String errorMessage,int status) {
        return new ApiOutput<>(status, null, errorMessage,null);
    }
}
