package com.atom.personnel.exception;

public class InvalidOperationException extends BusinessException {

    public InvalidOperationException() {}
    public InvalidOperationException(String message) {
        super(message);
    }
}
