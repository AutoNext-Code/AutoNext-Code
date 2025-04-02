package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;

@Service
public class ReservationStartService {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private EmailSenderService emailService;

    @Autowired
    private BookingRepository bookingRepository;

    public void start() {
        scheduler.scheduleAtFixedRate(this::checkReservationsStartingSoon, 0, 5, TimeUnit.MINUTES);
    }

    private void checkReservationsStartingSoon() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        LocalTime startTime = now.toLocalTime();
        LocalTime endTime = startTime.plusMinutes(10);

        List<Booking> bookings = bookingRepository.findReservationsToStartSoon(BookingStatus.Pending, date, startTime, endTime);
        for (Booking booking : bookings) {
            sendReservationNotification(booking);
            booking.setConfirmationStatus(ConfirmationStatus.PendingConfirmation);
        }
        bookingRepository.saveAll(bookings);
    }

    private void sendReservationNotification(Booking booking) {
        String subject = "Notificación de reserva";
        String body = "Tu reserva en " + booking.getParkingSpace().getName() +
                " inicia a las " + booking.getStartTime() + " del " + booking.getDate() +
                ". ¡Confirma tu llegada!";
        emailService.sendEmail(booking.getUser().getEmail(), subject, body);
    }
}

