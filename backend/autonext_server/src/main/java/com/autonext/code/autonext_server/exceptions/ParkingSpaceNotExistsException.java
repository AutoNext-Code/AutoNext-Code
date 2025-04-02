package com.autonext.code.autonext_server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ParkingSpaceNotExistsException extends RuntimeException {
    public ParkingSpaceNotExistsException(String message) {
        super(message);
    }
}
