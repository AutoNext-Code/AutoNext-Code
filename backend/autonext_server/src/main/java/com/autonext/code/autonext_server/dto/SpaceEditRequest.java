package com.autonext.code.autonext_server.dto;

import com.autonext.code.autonext_server.models.enums.ParkingState;

public class SpaceEditRequest {
    
    private int id;
    private int plugTypeId;
    private ParkingState status;

    
    public SpaceEditRequest() { }

    public SpaceEditRequest(int id, int plugTypeId, ParkingState status) {
        this.id = id;
        this.plugTypeId = plugTypeId;
        this.status = status;
    }

    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPlugTypeId() {
        return plugTypeId;
    }
    public void setPlugTypeId(int plugTypeId) {
        this.plugTypeId = plugTypeId;
    }
    public ParkingState getStatus() {
        return status;
    }
    public void setStatus(ParkingState status) {
        this.status = status;
    }
}
