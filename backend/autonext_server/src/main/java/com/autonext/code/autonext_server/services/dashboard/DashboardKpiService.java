package com.autonext.code.autonext_server.services.dashboard;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.specifications.BookingSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardKpiService {

    @Autowired
    private BookingRepository bookingRepository;

    public int calculateTotalDaysReserved(User user, Integer month, int year) {
        Specification<Booking> spec = baseSpec(user, month, year, ConfirmationStatus.Confirmed);
        List<Booking> bookings = bookingRepository.findAll(spec);

        return (int) bookings.stream()
                .map(Booking::getDate)
                .distinct()
                .count();
    }

    public int calculateTotalHoursReserved(User user, Integer month, int year) {
        Specification<Booking> spec = baseSpec(user, month, year, ConfirmationStatus.Confirmed);
        List<Booking> bookings = bookingRepository.findAll(spec);

        return bookings.stream()
                .filter(b -> b.getStartTime() != null && b.getEndTime() != null)
                .mapToInt(b -> (int) java.time.Duration.between(b.getStartTime(), b.getEndTime()).toHours())
                .sum();
    }

    public double calculateAverageSessionDuration(User user, Integer month, int year) {
        Specification<Booking> spec = baseSpec(user, month, year, ConfirmationStatus.Confirmed);
        List<Booking> bookings = bookingRepository.findAll(spec);

        return bookings.stream()
                .filter(b -> b.getStartTime() != null && b.getEndTime() != null)
                .mapToLong(b -> java.time.Duration.between(b.getStartTime(), b.getEndTime()).toMinutes())
                .average()
                .orElse(0.0) / 60.0;
    }

    public int calculateConfirmedReservations(User user, Integer month, int year) {
        Specification<Booking> spec = baseSpec(user, month, year, ConfirmationStatus.Confirmed);
        return (int) bookingRepository.count(spec);
    }

    public int calculateCancelledReservations(User user, Integer month, int year) {
        Specification<Booking> cancelledSpec = Specification
                .where(BookingSpecifications.hasUserId(user.getId()))
                .and(BookingSpecifications.hasYear(year))
                .and(month != null ? BookingSpecifications.hasMonth(month) : null)
                .and(BookingSpecifications.hasBookingStatus(BookingStatus.Cancelled));

        return (int) bookingRepository.count(cancelledSpec);
    }

    private Specification<Booking> baseSpec(User user, Integer month, int year, ConfirmationStatus status) {
        return Specification
                .where(BookingSpecifications.hasUserId(user.getId()))
                .and(month != null ? BookingSpecifications.hasMonth(month) : null)
                .and(BookingSpecifications.hasYear(year))
                .and(BookingSpecifications.hasConfirmationStatus(status));
    }
}