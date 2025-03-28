package com.autonext.code.autonext_server.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingDTO {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String userName;
    private String carPlate;

    public BookingDTO(LocalDate date, LocalTime startTime, LocalTime endTime, String userName, String carPlate) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userName = userName;
        this.carPlate = carPlate;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getUserName() {
        return userName;
    }

    public String getCarPlate() {
        return carPlate;
    }
}
