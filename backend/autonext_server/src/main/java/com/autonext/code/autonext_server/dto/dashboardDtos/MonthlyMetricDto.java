package com.autonext.code.autonext_server.dto.dashboardDtos;

public class MonthlyMetricDto {
  private String month;
  private int value;

  public MonthlyMetricDto() {
  }

  public MonthlyMetricDto(String month, int value) {
    this.month = month;
    this.value = value;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}