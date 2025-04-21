package com.autonext.code.autonext_server.services.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.dashboardDtos.DashboardSummaryDto;
import com.autonext.code.autonext_server.dto.dashboardDtos.MonthlyMetricDto;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.Strike;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;
import com.autonext.code.autonext_server.services.JwtService;
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

  @Autowired
  private DashboardKpiService dashboardKpiService;

  private static final Locale SPANISH = Locale.forLanguageTag("es-ES");

  public DashboardSummaryDto getDashboardForCurrentUser(int month, int year, String token) {
    int userId = jwtService.extractUserId(token);

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    DashboardSummaryDto dto = new DashboardSummaryDto();

    // KPIs
    dto.setTotalDaysReserved(dashboardKpiService.calculateTotalDaysReserved(user, month, year));
    dto.setTotalHoursReserved(dashboardKpiService.calculateTotalHoursReserved(user, month, year));
    dto.setAverageSessionDuration(dashboardKpiService.calculateAverageSessionDuration(user, month, year));
    dto.setConfirmedReservations(dashboardKpiService.calculateConfirmedReservations(user, month, year));
    dto.setUnconfirmedReservations(dashboardKpiService.calculateUnconfirmedReservations(user, month, year));

    dto.setMonthlyDaysReserved(calculateMonthlyDaysReserved(user, year));
    dto.setMonthlyHoursReserved(calculateMonthlyHoursReserved(user, year));
    dto.setMonthlyAvgDuration(calculateMonthlyAvgDuration(user, year));

    // Strikes
    int activeStrikes = (int) user.getStrikes().stream()
        .filter(Strike::isActive)
        .count();
    dto.setStrikes(activeStrikes);
    dto.setBanned(user.isBanned());

    return dto;
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


  private List<MonthlyMetricDto> calculateMonthlyHoursReserved(User user, int year) {
    List<MonthlyMetricDto> list = new ArrayList<>();

    for (int month = 1; month <= 12; month++) {
      int totalHours = calculateTotalHoursReserved(user, month, year);
      list.add(new MonthlyMetricDto(getMonthName(month), totalHours));
    }

    return list;
  }

  private List<MonthlyMetricDto> calculateMonthlyAvgDuration(User user, int year) {
    List<MonthlyMetricDto> list = new ArrayList<>();

    for (int month = 1; month <= 12; month++) {
      double avgDuration = calculateAverageSessionDuration(user, month, year);
      list.add(new MonthlyMetricDto(getMonthName(month), (int) Math.round(avgDuration)));
    }

    return list;
  }

  // Utils
  private String getMonthName(int month) {
    String name = java.time.Month.of(month)
        .getDisplayName(java.time.format.TextStyle.FULL, SPANISH);

    return name.substring(0, 1).toUpperCase() + name.substring(1);
  }

}