package com.autonext.code.autonext_server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.dashboardDtos.DashboardSummaryDto;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.Strike;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;
import com.autonext.code.autonext_server.specifications.BookingSpecifications;

import org.springframework.data.jpa.domain.Specification;

@Service
public class DashboardService {

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtService jwtService;

  public DashboardSummaryDto getDashboardForCurrentUser(int month, int year, String token) {
    int userId = jwtService.extractUserId(token);

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    DashboardSummaryDto dto = new DashboardSummaryDto();

    // KPIs
    dto.setTotalDaysReserved(calculateTotalDaysReserved(user, month, year));
    dto.setTotalHoursReserved(calculateTotalHoursReserved(user, month, year));
    dto.setAverageSessionDuration(calculateAverageSessionDuration(user, month, year));
    dto.setConfirmedReservations(calculateConfirmedReservations(user, month, year));
    dto.setUnconfirmedReservations(calculateUnconfirmedReservations(user, month, year));

    // Strikes
    int activeStrikes = (int) user.getStrikes().stream()
        .filter(Strike::isActive)
        .count();
    dto.setStrikes(activeStrikes);
    dto.setBanned(user.isBanned());

    return dto;
  }

  private int calculateTotalDaysReserved(User user, int month, int year) {
    Specification<Booking> spec = Specification
        .where(BookingSpecifications.hasUserId(user.getId()))
        .and(BookingSpecifications.hasMonth(month))
        .and(BookingSpecifications.hasYear(year))
        .and(BookingSpecifications.hasConfirmationStatus(ConfirmationStatus.Confirmed));

    List<Booking> bookings = bookingRepository.findAll(spec);

    return (int) bookings.stream()
        .map(Booking::getDate)
        .distinct()
        .count();
  }

  private int calculateTotalHoursReserved(User user, int month, int year) {
    Specification<Booking> spec = Specification
        .where(BookingSpecifications.hasUserId(user.getId()))
        .and(BookingSpecifications.hasMonth(month))
        .and(BookingSpecifications.hasYear(year))
        .and(BookingSpecifications.hasConfirmationStatus(ConfirmationStatus.Confirmed));

    List<Booking> bookings = bookingRepository.findAll(spec);

    return bookings.stream()
        .filter(b -> b.getStartTime() != null && b.getEndTime() != null)
        .mapToInt(b -> (int) java.time.Duration.between(b.getStartTime(), b.getEndTime()).toHours())
        .sum();
  }

  private double calculateAverageSessionDuration(User user, int month, int year) {
    Specification<Booking> spec = Specification
        .where(BookingSpecifications.hasUserId(user.getId()))
        .and(BookingSpecifications.hasMonth(month))
        .and(BookingSpecifications.hasYear(year))
        .and(BookingSpecifications.hasConfirmationStatus(ConfirmationStatus.Confirmed));

    List<Booking> bookings = bookingRepository.findAll(spec);

    return bookings.stream()
        .filter(b -> b.getStartTime() != null && b.getEndTime() != null)
        .mapToLong(b -> java.time.Duration.between(b.getStartTime(), b.getEndTime()).toMinutes())
        .average()
        .orElse(0.0) / 60.0;
  }

  private int calculateConfirmedReservations(User user, int month, int year) {
    Specification<Booking> spec = Specification
        .where(BookingSpecifications.hasUserId(user.getId()))
        .and(BookingSpecifications.hasMonth(month))
        .and(BookingSpecifications.hasYear(year))
        .and(BookingSpecifications.hasConfirmationStatus(ConfirmationStatus.Confirmed));

    return (int) bookingRepository.count(spec);
  }

  private int calculateUnconfirmedReservations(User user, int month, int year) {
    Specification<Booking> inactiveSpec = Specification
        .where(BookingSpecifications.hasUserId(user.getId()))
        .and(BookingSpecifications.hasMonth(month))
        .and(BookingSpecifications.hasYear(year))
        .and(BookingSpecifications.hasConfirmationStatus(ConfirmationStatus.Inactive));

    Specification<Booking> expiredSpec = Specification
        .where(BookingSpecifications.hasUserId(user.getId()))
        .and(BookingSpecifications.hasMonth(month))
        .and(BookingSpecifications.hasYear(year))
        .and(BookingSpecifications.hasConfirmationStatus(ConfirmationStatus.Expired));

    long inactiveCount = bookingRepository.count(inactiveSpec);
    long expiredCount = bookingRepository.count(expiredSpec);

    return (int) (inactiveCount + expiredCount);
  }
}