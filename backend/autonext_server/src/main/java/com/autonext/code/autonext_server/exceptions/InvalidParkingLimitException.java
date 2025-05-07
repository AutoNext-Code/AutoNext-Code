package com.autonext.code.autonext_server.exceptions;

public class InvalidParkingLimitException extends RuntimeException {
    public InvalidParkingLimitException(String message) {
        super(message);
    }
}
