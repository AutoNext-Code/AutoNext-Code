package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;

@Service
public class ReservationStartService implements CommandLineRunner {
  private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

  @Autowired
  private EmailTemplateService emailTemplateService;

  @Autowired
  private BookingRepository bookingRepository;

  @Override
  public void run(String... args) throws Exception {
    start();
  }

  public void start() {
    scheduler.scheduleAtFixedRate(this::checkReservationsStartingSoon, 0, 5, TimeUnit.MINUTES);
  }

  private void checkReservationsStartingSoon() {
    LocalDateTime now = LocalDateTime.now();
    LocalDate date = now.toLocalDate();
    LocalTime startTime = now.toLocalTime();
    LocalTime endTime = startTime.plusMinutes(10);

    List<Booking> bookings = bookingRepository.findReservationsToStartSoon(BookingStatus.Pending, date, startTime,
        endTime);

    if (!bookings.isEmpty()) {
      for (Booking booking : bookings) {
        if (booking.getConfirmationStatus() == ConfirmationStatus.Inactive) {
          this.emailTemplateService.notifyUserOnReservationStart(booking);
          booking.setConfirmationStatus(ConfirmationStatus.PendingConfirmation);
        }
      }
      bookingRepository.saveAll(bookings);
    }
  }
}