package com.autonext.code.autonext_server.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingDTO {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String userName;
    private String carPlate;
    private String status;
    private String parkingSpace;

    public BookingDTO(LocalDate date, LocalTime startTime, LocalTime endTime, String userName, String carPlate, String status, String parkingSpace) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userName = userName;
        this.carPlate = carPlate;
        this.status = status;
        this.parkingSpace = parkingSpace;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(String parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

}
