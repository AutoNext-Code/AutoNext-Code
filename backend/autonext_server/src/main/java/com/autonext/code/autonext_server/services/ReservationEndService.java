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
public class ReservationEndService implements CommandLineRunner  {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private EmailSenderService emailService;

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
        System.out.println("\033[0;34mEntra en backgrundService ReservationEndService\033[0m");
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        LocalTime startTime = now.toLocalTime();
        LocalTime endTime = startTime.minusMinutes(15);

        List<Booking> bookings = bookingRepository.findReservationsToEndSoon(BookingStatus.Active, date, startTime, endTime);
        for (Booking booking : bookings) {
            sendReservationEndNotification(booking);
        }
    }

    private void sendReservationEndNotification(Booking booking) {
        String subject = "Tu reserva está por finalizar";
        String body = "La reserva en " + booking.getParkingSpace().getName() +
                " termina a las " + booking.getEndTime() + " del " + booking.getDate() +
                ". ¡Recuerda liberar el espacio!";
        emailService.sendEmail(booking.getUser().getEmail(), subject, body);
    }
}
