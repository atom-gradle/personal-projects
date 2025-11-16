package com.ganzhi.exception;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String resourceName, Object identifier) {
        super("404", String.format("%s [%s] 未找到", resourceName, identifier));
    }
}