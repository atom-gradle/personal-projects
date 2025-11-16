package com.ganzhi.exception;

public class BusinessException extends RuntimeException {
    private String code;
    private String message;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
        this.code = "500";
    }

    // getters
    public String getCode() { return code; }
    public String getMessage() { return message; }
}