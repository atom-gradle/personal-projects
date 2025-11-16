package com.ganzhi.exception;

public class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super("400", message);
    }

    public ValidationException(String code, String message) {
        super(code, message);
    }
}
