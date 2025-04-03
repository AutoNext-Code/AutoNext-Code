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
public class ReservationCompletionService implements CommandLineRunner {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void run(String... args) throws Exception {
        start();
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::completeExpiredReservations, 0, 5, TimeUnit.MINUTES);
    }

    private void completeExpiredReservations() {

        System.out.println("\033[0;34mEntra en backgrundService\033[0m");

        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();

        List<Booking> completedBookings = bookingRepository.findCompletedBookings(BookingStatus.Active, date, currentTime);
        for (Booking booking : completedBookings) {
            booking.setStatus(BookingStatus.Completed);
        }
        bookingRepository.saveAll(completedBookings);
    }
}
