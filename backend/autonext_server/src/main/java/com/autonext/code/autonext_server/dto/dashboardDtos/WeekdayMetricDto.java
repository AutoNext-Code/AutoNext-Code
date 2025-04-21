package com.autonext.code.autonext_server.dto.dashboardDtos;

public class WeekdayMetricDto {

  private String day;
  private int totalHours;

  public WeekdayMetricDto() {
  }

  public WeekdayMetricDto(String day, int totalHours) {
    this.day = day;
    this.totalHours = totalHours;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public int getTotalHours() {
    return totalHours;
  }

  public void setTotalHours(int totalHours) {
    this.totalHours = totalHours;
  }
}
