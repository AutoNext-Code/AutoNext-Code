package com.autonext.code.autonext_server.dto.dashboardDtos;

public class MonthlyConfirmationDto {

  private String month;
  private int confirmed;
  private int unconfirmed;

  public MonthlyConfirmationDto() {
  }

  public MonthlyConfirmationDto(String month, int confirmed, int unconfirmed) {
    this.month = month;
    this.confirmed = confirmed;
    this.unconfirmed = unconfirmed;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public int getConfirmed() {
    return confirmed;
  }

  public void setConfirmed(int confirmed) {
    this.confirmed = confirmed;
  }

  public int getUnconfirmed() {
    return unconfirmed;
  }

  public void setUnconfirmed(int unconfirmed) {
    this.unconfirmed = unconfirmed;
  }
}
