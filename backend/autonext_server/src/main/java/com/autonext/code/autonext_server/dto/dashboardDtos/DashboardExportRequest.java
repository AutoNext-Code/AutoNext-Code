package com.autonext.code.autonext_server.dto.dashboardDtos;

public class DashboardExportRequest {

  private Integer month;
  private int year;

  // Gráficas que llegan desde el front
  private String daysPerMonthChart;
  private String hoursPerMonthChart;
  private String avgDurationPerMonthChart;
  private String hoursPerWeekdayChart;
  private String confirmationsChart;

  public DashboardExportRequest() {
  }

  public Integer getMonth() {
    return month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getDaysPerMonthChart() {
    return daysPerMonthChart;
  }

  public void setDaysPerMonthChart(String daysPerMonthChart) {
    this.daysPerMonthChart = daysPerMonthChart;
  }

  public String getHoursPerMonthChart() {
    return hoursPerMonthChart;
  }

  public void setHoursPerMonthChart(String hoursPerMonthChart) {
    this.hoursPerMonthChart = hoursPerMonthChart;
  }

  public String getAvgDurationPerMonthChart() {
    return avgDurationPerMonthChart;
  }

  public void setAvgDurationPerMonthChart(String avgDurationPerMonthChart) {
    this.avgDurationPerMonthChart = avgDurationPerMonthChart;
  }

  public String getHoursPerWeekdayChart() {
    return hoursPerWeekdayChart;
  }

  public void setHoursPerWeekdayChart(String hoursPerWeekdayChart) {
    this.hoursPerWeekdayChart = hoursPerWeekdayChart;
  }

  public String getConfirmationsChart() {
    return confirmationsChart;
  }

  public void setConfirmationsChart(String confirmationsChart) {
    this.confirmationsChart = confirmationsChart;
  }
}