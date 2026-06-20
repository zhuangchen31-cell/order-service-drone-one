package com.uav.management.exception;

public class ValidationException extends BusinessException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String errorCode, String message) {
        super(errorCode, message);
    }
}
