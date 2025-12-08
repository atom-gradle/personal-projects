package com.atom.personnel.exception;

public class EmployeeNotExistsException extends BusinessException {
    public EmployeeNotExistsException() {}
    public EmployeeNotExistsException(String message) {
        super(message);
    }
}
