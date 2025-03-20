package com.autonext.code.autonext_server.exceptions;

public class ErrorSendEmailException extends RuntimeException {
    public ErrorSendEmailException(String message) {
        super(message);
    }
}
