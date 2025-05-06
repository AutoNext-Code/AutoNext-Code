package com.autonext.code.autonext_server.exceptions;

public class WorkCenterNotFoundException extends RuntimeException {
    public WorkCenterNotFoundException(String message) {
        super(message);
    }
}