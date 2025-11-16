package com.ganzhi.exception;

public class AuthorizationException extends BusinessException {
    public AuthorizationException(String message) {
        super("403", message);
    }
}