package com.ganzhi.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
    private boolean success;

    public ApiResponse() {}

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
    public ApiResponse(int code, String message, T data) {
        this(message, data);
        this.code = code;
    }

    //response.data -> whole
    //response.data.data -> apiResponse
    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>("操作成功！",data);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(int code,T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>(code,"error",data);
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(code));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String code,T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>(Integer.parseInt(code),"error",data);
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(code));
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public T getData() {
        return data;
    }
}
