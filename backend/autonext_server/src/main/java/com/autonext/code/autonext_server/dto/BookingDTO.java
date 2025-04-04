package com.autonext.code.autonext_server.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingDTO {
    private int id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String userName;
    private String nameSpace;
    private String delegation;
    private String status;
    private String confirmationStatus;
    private String carName;

    public BookingDTO(int id, LocalDate date, LocalTime startTime, LocalTime endTime, String userName, String nameSpace,
            String delegation, String status, String confirmationStatus, String carName) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userName = userName;
        this.nameSpace = nameSpace;
        this.delegation = delegation;
        this.status = status;
        this.confirmationStatus = confirmationStatus;
        this.carName = carName;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
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

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelegation() {
        return delegation;
    }

    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }

    public String getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(String confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public String getCarName() {
        return carName;
    }
    
    public void setCarName(String carName) {
        this.carName = carName;
    }
}
