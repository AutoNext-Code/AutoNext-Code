package com.autonext.code.autonext_server.exceptions;

public class CarPlateAlreadyExistsException extends RuntimeException {
    public CarPlateAlreadyExistsException(String message) {
        super(message);
    }
}
