package com.autonext.code.autonext_server.dto;

import java.time.LocalDate;

public class BookCheckDTO {
    
    private LocalDate date;
    private String startTime;
    private String endTime;

    public BookCheckDTO(LocalDate date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
