package com.autonext.code.autonext_server.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class BackgroundService {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private EmailSenderService emailService;

    @Autowired
    private BookingRepository bookingRepository;

    public void start() {
        scheduler.scheduleAtFixedRate(this::checkReservations, 20, 20, TimeUnit.MINUTES);
    }

    private void checkReservations() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        LocalTime startTime = now.toLocalTime();
        LocalTime endTime = now.toLocalTime().plusMinutes(10);

        List<Booking> bookings = bookingRepository.findReservationsToStartSoon(BookingStatus.Pending, date, startTime,
                endTime);

        for (Booking booking : bookings) {
            sendReservationNotification(booking);
        }
    }

    private void sendReservationNotification(Booking booking) {
        String subject = "Notificación de reserva";
        String body = "La reserva en el puesto de carga " + booking.getParkingSpace() + " está por iniciar a las "
                + booking.getStartTime() + " del día " + booking.getDate();
        emailService.sendEmail(booking.getUser().getEmail(), subject, body);
    }
}
