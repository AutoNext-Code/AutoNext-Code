package com.autonext.code.autonext_server.dto;

public class EditDto<T> {
    private String message;
    private T data;
  
    public EditDto() {
    }
    
    public EditDto(String message, T data) {
      this.message = message;
      this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
}
  