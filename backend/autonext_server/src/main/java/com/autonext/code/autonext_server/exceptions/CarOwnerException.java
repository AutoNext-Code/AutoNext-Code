package com.autonext.code.autonext_server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CarOwnerException extends RuntimeException{

    public CarOwnerException(String message){
        super(message);
    }
    
}
