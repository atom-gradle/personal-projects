package com.atom.personnel.exception;

public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException() {}
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
