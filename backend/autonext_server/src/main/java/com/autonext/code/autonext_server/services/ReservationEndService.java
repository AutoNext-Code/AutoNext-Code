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
import com.autonext.code.autonext_server.repositories.BookingRepository;

@Service
public class ReservationEndService implements CommandLineRunner {
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
    scheduler.scheduleAtFixedRate(this::checkReservationsEndingSoon, 0, 10, TimeUnit.MINUTES);
  }

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
