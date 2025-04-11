package com.autonext.code.autonext_server.dto;

import com.autonext.code.autonext_server.models.enums.PlugType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CarDTO {

    @JsonProperty(required = false)
    private int id;

    private String carPlate;

    private String name;

    private PlugType plugType;

    public CarDTO(int id, String carPlate, String name, PlugType plugType) {
        this.id = id;
        this.carPlate = carPlate;
        this.name = name;
        this.plugType = plugType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlugType getPlugType() {
        return plugType;
    }

    public void setPlugType(PlugType plugType) {
        this.plugType = plugType;
    }

    
    
}
