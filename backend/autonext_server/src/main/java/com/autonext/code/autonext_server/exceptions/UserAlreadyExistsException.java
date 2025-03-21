package com.autonext.code.autonext_server.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String message) {
      super(message);
  }
}
