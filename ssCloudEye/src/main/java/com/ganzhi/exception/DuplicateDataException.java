package com.ganzhi.exception;

public class DuplicateDataException extends BusinessException {
    public DuplicateDataException(String message) {
        super("409", message);
    }
}