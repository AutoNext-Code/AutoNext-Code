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
  private String cancelledChart;

  private int strikes;
  private boolean penalized;

  private int totalDaysReserved;
  private int totalHoursReserved;
  private double averageSessionDuration;
  private int totalWeeklyHoursReserved;
  private int confirmedReservations;
  private int cancelledReservations;

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

  public String getCancelledChart() {
    return cancelledChart;
  }

  public void setCancelledChart(String cancelledChart) {
    this.cancelledChart = cancelledChart;
  }

  public String getConfirmationsChart() {
    return confirmationsChart;
  }

  public void setConfirmationsChart(String confirmationsChart) {
    this.confirmationsChart = confirmationsChart;
  }

  public int getTotalDaysReserved() {
    return totalDaysReserved;
  }

  public void setTotalDaysReserved(int totalDaysReserved) {
    this.totalDaysReserved = totalDaysReserved;
  }

  public int getTotalHoursReserved() {
    return totalHoursReserved;
  }

  public void setTotalHoursReserved(int totalHoursReserved) {
    this.totalHoursReserved = totalHoursReserved;
  }

  public double getAverageSessionDuration() {
    return averageSessionDuration;
  }

  public void setAverageSessionDuration(double averageSessionDuration) {
    this.averageSessionDuration = averageSessionDuration;
  }

  public int getConfirmedReservations() {
    return confirmedReservations;
  }

  public void setConfirmedReservations(int confirmedReservations) {
    this.confirmedReservations = confirmedReservations;
  }

  public int getCancelledReservations() {
    return cancelledReservations;
  }

  public void setCancelledReservations(int cancelledReservations) {
    this.cancelledReservations = cancelledReservations;
  }

  public int getTotalWeeklyHoursReserved() {
    return totalWeeklyHoursReserved;
  }

  public void setTotalWeeklyHoursReserved(int totalWeeklyHoursReserved) {
    this.totalWeeklyHoursReserved = totalWeeklyHoursReserved;
  }

  public int getStrikes() {
    return strikes;
  }

  public void setStrikes(int strikes) {
    this.strikes = strikes;
  }

  public boolean isPenalized() {
    return penalized;
  }

  public void setPenalized(boolean penalized) {
    this.penalized = penalized;
  }
}