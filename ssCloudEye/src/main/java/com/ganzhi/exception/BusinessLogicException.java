package com.ganzhi.exception;

public class BusinessLogicException extends BusinessException {
    public BusinessLogicException(String message) {
        super("422", message);
    }
}