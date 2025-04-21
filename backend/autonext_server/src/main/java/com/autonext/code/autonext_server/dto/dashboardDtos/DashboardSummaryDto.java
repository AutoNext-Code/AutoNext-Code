package com.autonext.code.autonext_server.dto.dashboardDtos;

import java.util.List;

public class DashboardSummaryDto {
  private int strikes;
  private boolean banned;
  private int totalDaysReserved;
  private int totalHoursReserved;
  private double averageSessionDuration;
  private int confirmedReservations;
  private int unconfirmedReservations;

  private List<MonthlyMetricDto> monthlyDaysReserved;
  private List<MonthlyMetricDto> monthlyHoursReserved;
  private List<MonthlyMetricDto> monthlyAvgDuration;

  private List<MonthlyConfirmationDto> monthlyConfirmationStats;

  private List<WeekdayMetricDto> weeklyHoursReserved;

  public DashboardSummaryDto() {
  }

  public int getStrikes() {
    return strikes;
  }

  public void setStrikes(int strikes) {
    this.strikes = strikes;
  }

  public boolean isBanned() {
    return banned;
  }

  public void setBanned(boolean banned) {
    this.banned = banned;
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

  public int getUnconfirmedReservations() {
    return unconfirmedReservations;
  }

  public void setUnconfirmedReservations(int unconfirmedReservations) {
    this.unconfirmedReservations = unconfirmedReservations;
  }

  public List<MonthlyMetricDto> getMonthlyDaysReserved() {
    return monthlyDaysReserved;
  }

  public void setMonthlyDaysReserved(List<MonthlyMetricDto> monthlyDaysReserved) {
    this.monthlyDaysReserved = monthlyDaysReserved;
  }

  public List<MonthlyMetricDto> getMonthlyHoursReserved() {
    return monthlyHoursReserved;
  }

  public void setMonthlyHoursReserved(List<MonthlyMetricDto> monthlyHoursReserved) {
    this.monthlyHoursReserved = monthlyHoursReserved;
  }

  public List<MonthlyMetricDto> getMonthlyAvgDuration() {
    return monthlyAvgDuration;
  }

  public void setMonthlyAvgDuration(List<MonthlyMetricDto> monthlyAvgDuration) {
    this.monthlyAvgDuration = monthlyAvgDuration;
  }

  public List<MonthlyConfirmationDto> getMonthlyConfirmationStats() {
    return monthlyConfirmationStats;
  }

  public void setMonthlyConfirmationStats(List<MonthlyConfirmationDto> monthlyConfirmationStats) {
    this.monthlyConfirmationStats = monthlyConfirmationStats;
  }

  public List<WeekdayMetricDto> getWeeklyHoursReserved() {
    return weeklyHoursReserved;
  }

  public void setWeeklyHoursReserved(List<WeekdayMetricDto> weeklyHoursReserved) {
    this.weeklyHoursReserved = weeklyHoursReserved;
  }
}