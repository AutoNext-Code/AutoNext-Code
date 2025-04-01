package com.autonext.code.autonext_server.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class MapBookingDTO {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int userId; 
    private int carId; 
    private String carPlate;
    private String status;
    private int parkingSpaceId ;
    private int parkingLevelId ;

    public MapBookingDTO(LocalDate date, LocalTime startTime, LocalTime endTime, String userName, String carPlate, String status) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.carPlate = carPlate;
        this.status = status;
        this.parkingSpaceId = parkingSpaceId;
        this.parkingLevelId = parkingLevelId;
    }

    public MapBookingDTO(LocalDate date, LocalTime startTime, LocalTime endTime, String userName, int carId, String status) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.carId = carId;
        this.status = status;
        this.parkingSpaceId = parkingSpaceId;
        this.parkingLevelId = parkingLevelId;
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

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(int parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public int getParkingLevelId() {
        return parkingLevelId;
    }

    public void setParkingLevelId(int parkingLevelId) {
        this.parkingLevelId = parkingLevelId;
    }



}
