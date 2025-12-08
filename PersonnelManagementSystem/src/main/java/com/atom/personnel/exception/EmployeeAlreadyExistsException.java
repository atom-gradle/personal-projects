package com.atom.personnel.exception;

public class EmployeeAlreadyExistsException extends BusinessException {
    public EmployeeAlreadyExistsException() {}
    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }
}
