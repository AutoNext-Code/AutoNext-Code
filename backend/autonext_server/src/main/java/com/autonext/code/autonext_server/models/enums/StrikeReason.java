package com.autonext.code.autonext_server.models.enums;

public enum StrikeReason {
    NOTCONFIRMED("La reserva no ha sido confirmada", 1),
    DAMAGEDSPACE("La plaza ha sido dañada durante su uso", 2);

    private final String message;
    private final int code;

    StrikeReason(String message, int code){
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }


    
}
