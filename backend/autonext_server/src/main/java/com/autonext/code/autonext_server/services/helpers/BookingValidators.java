package com.autonext.code.autonext_server.services.helpers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.autonext.code.autonext_server.dto.MapBookingDTO;
import com.autonext.code.autonext_server.exceptions.ParkingSpaceOccupiedException;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;

@Component
public class BookingValidators {

  private final BookingRepository bookingRepository;

  public BookingValidators(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  public void validate(MapBookingDTO dto, ParkingSpace parkingSpace) {
    validateDateAndTime(dto);
    checkAvailability(dto, parkingSpace);
  }

  private void validateDateAndTime(MapBookingDTO dto) {
    LocalDate today = LocalDate.now();
    LocalDate maxAllowed = today.plusWeeks(1);

    if (dto.getDate().isBefore(today)) {
      throw new IllegalArgumentException("La fecha no puede ser anterior a la actual");
    }

    if (dto.getDate().isAfter(maxAllowed)) {
      throw new IllegalArgumentException("Solo puedes reservar hasta una semana vista");
    }

    if (dto.getStartTime().equals(dto.getEndTime())) {
      throw new IllegalArgumentException("La hora de inicio y fin no pueden ser iguales");
    }

    if (dto.getDate().isEqual(today) && dto.getStartTime().isBefore(LocalTime.now())) {
      throw new IllegalArgumentException("La hora de inicio no puede ser anterior a la actual");
    }
  }

  private void checkAvailability(MapBookingDTO dto, ParkingSpace parkingSpace) {
    List<Booking> bookings = bookingRepository.findAllReservationsByDateAndSpace(dto.getDate(), parkingSpace);

    for (Booking booking : bookings) {
      boolean overlap = !(dto.getEndTime().isBefore(booking.getStartTime()) ||
          dto.getStartTime().isAfter(booking.getEndTime()));

      if (overlap && (booking.getStatus() == BookingStatus.Active || booking.getStatus() == BookingStatus.Pending)) {
        throw new ParkingSpaceOccupiedException("La plaza está ocupada en el horario seleccionado");
      }
    }
  }
}
