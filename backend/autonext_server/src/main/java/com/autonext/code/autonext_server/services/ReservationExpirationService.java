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
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.models.enums.StrikeReason;
import com.autonext.code.autonext_server.repositories.BookingRepository;

@Service
public class ReservationExpirationService {

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private StrikeService strikeService;


  @Scheduled(fixedRate = 60 * 1000)
  private void checkReservationsExpiredSoon() {
    LocalDateTime now = LocalDateTime.now();
    LocalDate date = now.toLocalDate();
    LocalTime expiredTime = now.toLocalTime().minusMinutes(20);

    List<Booking> bookings = bookingRepository.findExpiredPendingConfirmations(
        ConfirmationStatus.PendingConfirmation, date, expiredTime);

    if (!bookings.isEmpty()) {
      for (Booking booking : bookings) {
        if (booking.getStatus() == BookingStatus.Pending) {

          booking.setConfirmationStatus(ConfirmationStatus.Expired);
          strikeService.setBookingStrike(booking, date, now.toLocalTime(), StrikeReason.NOTCONFIRMED);
          

          // TODO: Aqui es donde se informara que recivio un strike
        }
      }

      bookingRepository.saveAll(bookings);
    }

  }
}
