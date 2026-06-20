package com.uav.management.exception;

public class DataNotFoundException extends BusinessException {

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String errorCode, String message) {
        super(errorCode, message);
    }
}
