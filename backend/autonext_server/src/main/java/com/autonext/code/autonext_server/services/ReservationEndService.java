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
public class ReservationEndService  {

  @Autowired
  private EmailTemplateService emailTemplateService;

  @Autowired
  private BookingRepository bookingRepository;

  @Scheduled(fixedRate = 60 * 1000)
  private void checkReservationsEndingSoon() {
    LocalDateTime now = LocalDateTime.now();
    LocalDate date = now.toLocalDate();

    LocalTime lowerBound = now.toLocalTime().plusMinutes(5);
    LocalTime endTime = now.toLocalTime().plusMinutes(15);

    List<Booking> bookings = bookingRepository.findReservationsToEndSoon(BookingStatus.Active, date, lowerBound,
        endTime);

    if (!bookings.isEmpty()) {
      for (Booking booking : bookings) {
        this.emailTemplateService.sendReservationEndNotification(booking);
      }
    }

  }
}
