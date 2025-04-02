package com.autonext.code.autonext_server.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.autonext.code.autonext_server.models.ParkingLevel;
import com.autonext.code.autonext_server.models.WorkCenter;

public class ParkingCenterDTO {
    private String centerName;
    private List<Level> parkingLevels;

    public ParkingCenterDTO(WorkCenter center){
        this.centerName = center.name;
        this.parkingLevels = center.getParkingLevels().stream()
            .map(Level::new)
            .collect(Collectors.toList());
    }

    public String getCenterName() {
        return centerName;
    }

    public List<Level> getParkingLevels() {
        return parkingLevels;
    }

    public static class Level{
        private int id;
        private String name;

        public Level(ParkingLevel level){
            this.id = level.id;
            this.name = level.name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        
    }

}
