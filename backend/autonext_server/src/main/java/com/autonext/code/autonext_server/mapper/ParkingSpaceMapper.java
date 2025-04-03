package com.autonext.code.autonext_server.mapper;

import com.autonext.code.autonext_server.dto.ParkingLevelMapDTO;
import com.autonext.code.autonext_server.dto.ParkingSpaceDTO;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.models.enums.ParkingState;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ParkingSpaceMapper {

  public static ParkingLevelMapDTO.Space toSpaceDTO(ParkingSpace space,
      LocalDate date,
      String startTimeStr,
      String endTimeStr,
      User user) {
    ParkingState status = ParkingState.Available;

    if (date != null && startTimeStr != null && endTimeStr != null) {
      LocalTime startTime = LocalTime.parse(startTimeStr);
      LocalTime endTime = LocalTime.parse(endTimeStr);

      List<Booking> overlappingBookings = space.getBookings().stream()
          .filter(Booking::isActive)
          .filter(b -> b.getDate().equals(date) &&
              overlaps(b.getStartTime(), b.getEndTime(), startTime, endTime))
          .toList();

      if (!overlappingBookings.isEmpty()) {
        Booking booking = overlappingBookings.get(0);
        if (booking.getUser().getId() == user.getId()) {
          status = ParkingState.Own_Reservation;
        } else {
          status = ParkingState.Occupied;
        }
      }
    }

    return new ParkingLevelMapDTO.Space(
        space.getId(),
        space.getX(),
        space.getY(),
        space.getDirection().name(),
        space.getPlugType().name(),
        status.name());
  }

  private static boolean overlaps(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
    return !start1.isAfter(end2) && !start2.isAfter(end1);
  }
}