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
public class ReservationExpirationService implements CommandLineRunner {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void run(String... args) throws Exception {
        start();
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::checkReservationsExpiredSoon, 0, 5, TimeUnit.MINUTES);
    }

    private void checkReservationsExpiredSoon() {
        System.out.println("\033[0;31mEntra en backgrundService ReservationExpirationService\033[0m");

        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        LocalTime expiredTime = now.toLocalTime().minusMinutes(15);
    
        List<Booking> bookings = bookingRepository.findExpiredPendingConfirmations(
                ConfirmationStatus.PendingConfirmation, date, expiredTime);

        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.Pending) {
                booking.setConfirmationStatus(ConfirmationStatus.Expired);
                booking.setStatus(BookingStatus.Strike);
                //TODO: Aqui es donde se penaliza al usuario cuando llegue la historia de usuario
                //TODO: Aqui es donde se informara que recivio un strike
            }
        }

        bookingRepository.saveAll(bookings);
    }
}
