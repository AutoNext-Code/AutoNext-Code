package com.autonext.code.autonext_server.services.dashboard;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.User;
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

    public int calculateTotalDaysReserved(User user, int month, int year) {
        Specification<Booking> spec = baseSpec(user, month, year, ConfirmationStatus.Confirmed);
        List<Booking> bookings = bookingRepository.findAll(spec);

        return (int) bookings.stream()
            .map(Booking::getDate)
            .distinct()
            .count();
    }

    public int calculateTotalHoursReserved(User user, int month, int year) {
        Specification<Booking> spec = baseSpec(user, month, year, ConfirmationStatus.Confirmed);
        List<Booking> bookings = bookingRepository.findAll(spec);

        return bookings.stream()
            .filter(b -> b.getStartTime() != null && b.getEndTime() != null)
            .mapToInt(b -> (int) java.time.Duration.between(b.getStartTime(), b.getEndTime()).toHours())
            .sum();
    }

    public double calculateAverageSessionDuration(User user, int month, int year) {
        Specification<Booking> spec = baseSpec(user, month, year, ConfirmationStatus.Confirmed);
        List<Booking> bookings = bookingRepository.findAll(spec);

        return bookings.stream()
            .filter(b -> b.getStartTime() != null && b.getEndTime() != null)
            .mapToLong(b -> java.time.Duration.between(b.getStartTime(), b.getEndTime()).toMinutes())
            .average()
            .orElse(0.0) / 60.0;
    }

    public int calculateConfirmedReservations(User user, int month, int year) {
        Specification<Booking> spec = baseSpec(user, month, year, ConfirmationStatus.Confirmed);
        return (int) bookingRepository.count(spec);
    }

    public int calculateUnconfirmedReservations(User user, int month, int year) {
        Specification<Booking> inactive = baseSpec(user, month, year, ConfirmationStatus.Inactive);
        Specification<Booking> expired = baseSpec(user, month, year, ConfirmationStatus.Expired);

        return (int) (bookingRepository.count(inactive) + bookingRepository.count(expired));
    }

    private Specification<Booking> baseSpec(User user, int month, int year, ConfirmationStatus status) {
        return Specification
            .where(BookingSpecifications.hasUserId(user.getId()))
            .and(BookingSpecifications.hasMonth(month))
            .and(BookingSpecifications.hasYear(year))
            .and(BookingSpecifications.hasConfirmationStatus(status));
    }
}