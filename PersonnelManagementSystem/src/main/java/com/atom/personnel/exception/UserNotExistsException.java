package com.atom.personnel.exception;

public class UserNotExistsException extends BusinessException {

    public UserNotExistsException() {}
    public UserNotExistsException(String message) {
        super(message);
    }
}
