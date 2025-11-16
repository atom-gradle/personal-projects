package com.ganzhi.exception;

public class SystemBusyException extends BusinessException {
    public SystemBusyException(String message) {
        super("503", message);
    }
}