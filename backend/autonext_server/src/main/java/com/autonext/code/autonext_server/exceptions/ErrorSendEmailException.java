package com.autonext.code.autonext_server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ErrorSendEmailException extends RuntimeException {
    public ErrorSendEmailException(String message) {
        super(message);
    }
}
