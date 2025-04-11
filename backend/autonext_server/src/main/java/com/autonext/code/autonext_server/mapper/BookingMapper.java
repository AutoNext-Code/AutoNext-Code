package com.autonext.code.autonext_server.mapper;

import com.autonext.code.autonext_server.dto.BookingDTO;
import com.autonext.code.autonext_server.models.Booking;

public class BookingMapper {

  public static BookingDTO toDTO(Booking booking) {
    if (booking == null)
      return null;

      String carName = booking.getCar() != null
      ? (booking.getCar().getName() != null && !booking.getCar().getName().isBlank()
          ? booking.getCar().getName()
          : booking.getCar().getCarPlate())
      : "Vehículo eliminado";
  

    return new BookingDTO(
        booking.getId(),
        booking.getDate(),
        booking.getStartTime(),
        booking.getEndTime(),
        booking.getUser().getName() + " " + booking.getUser().getSurname(),
        booking.getParkingSpace().getName(),
        booking.getWorkCenter().getName(),
        booking.getStatus().name(),
        booking.getConfirmationStatus().name(),
        carName);
  }
}