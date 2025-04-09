package com.autonext.code.autonext_server.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;

@Service
public class ReservationStartService {

  @Autowired
  private EmailTemplateService emailTemplateService;

  @Autowired
  private BookingRepository bookingRepository;

  @Scheduled(fixedRate = 60 * 1000)
  private void checkReservationsStartingSoon() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime windowStart = now.minusMinutes(15);  
    LocalDateTime windowEnd = now.plusMinutes(15); 

    List<Booking> bookings = bookingRepository.findReservationsToStartSoon(
        BookingStatus.Pending, windowStart, windowEnd);

    if (!bookings.isEmpty()) {
        for (Booking booking : bookings) {
            if (booking.getConfirmationStatus() == ConfirmationStatus.Inactive) {
                booking.setConfirmationStatus(ConfirmationStatus.PendingConfirmation);
                bookingRepository.save(booking);
                emailTemplateService.notifyUserOnReservationStart(booking);
            }
        }
        bookingRepository.saveAll(bookings);
    }
}



}