package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;

@Service
public class ReservationCompletionService {

  @Autowired
  private BookingRepository bookingRepository;

  @Scheduled(fixedRate = 60 * 1000)
  private void completeExpiredReservations() {
    LocalDateTime now = LocalDateTime.now();
    LocalDate date = now.toLocalDate();
    LocalTime currentTime = now.toLocalTime();

    List<Booking> completedBookings = bookingRepository.findCompletedBookings(BookingStatus.Active, date, currentTime);

    if (!completedBookings.isEmpty()) {
      for (Booking booking : completedBookings) {
        booking.setStatus(BookingStatus.Completed);
      }
      bookingRepository.saveAll(completedBookings);
    }

  }
}
