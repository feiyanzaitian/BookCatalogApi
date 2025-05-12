package com.saxu.bookcatalog.model;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private int code;

    // 成功时构造
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, "Success", 200);
    }

    // 失败时构造
    public static <T> ApiResponse<T> error(String message, int code) {
        return new ApiResponse<>(false, null, message, code);
    }

    // 全参构造器
    private ApiResponse(boolean success, T data, String message, int code) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.code = code;
    }

}