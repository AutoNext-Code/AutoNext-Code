package com.autonext.code.autonext_server.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String message) {
      super(message);
  }
}
