package com.autonext.code.autonext_server.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class MapBookingDTO {
    
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer carId; 
    private Integer parkingSpaceId ;
    


    public MapBookingDTO() {}

    public MapBookingDTO(LocalDate date, LocalTime startTime, LocalTime endTime, String userName, Integer carId, Integer parkingSpaceId) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.carId = carId;
        this.parkingSpaceId = parkingSpaceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(Integer parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }
}
