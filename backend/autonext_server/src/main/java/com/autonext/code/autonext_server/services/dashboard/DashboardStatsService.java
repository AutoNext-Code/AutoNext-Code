package com.autonext.code.autonext_server.services.dashboard;

import com.autonext.code.autonext_server.dto.dashboardDtos.MonthlyConfirmationDto;
import com.autonext.code.autonext_server.dto.dashboardDtos.WeekdayMetricDto;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.specifications.BookingSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardStatsService {

  private static final Locale SPANISH = Locale.forLanguageTag("es-ES");

  @Autowired
  private BookingRepository bookingRepository;

  public List<WeekdayMetricDto> calculateWeekdayHoursReserved(User user, Integer month, int year) {
    Specification<Booking> spec = Specification
        .where(BookingSpecifications.hasUserId(user.getId()))
        .and(BookingSpecifications.hasMonth(month))
        .and(BookingSpecifications.hasYear(year))
        .and(BookingSpecifications.hasConfirmationStatus(ConfirmationStatus.Confirmed));

    List<Booking> bookings = bookingRepository.findAll(spec);

    Map<DayOfWeek, Integer> weekdayTotals = new EnumMap<>(DayOfWeek.class);
    for (DayOfWeek day : DayOfWeek.values()) {
      weekdayTotals.put(day, 0);
    }

    for (Booking booking : bookings) {
      if (booking.getStartTime() != null && booking.getEndTime() != null && booking.getDate() != null) {
        DayOfWeek day = booking.getDate().getDayOfWeek();
        int hours = (int) java.time.Duration.between(booking.getStartTime(), booking.getEndTime()).toHours();
        weekdayTotals.put(day, weekdayTotals.get(day) + hours);
      }
    }

    return Arrays.stream(DayOfWeek.values())
        .map(day -> new WeekdayMetricDto(
            capitalize(day.getDisplayName(TextStyle.FULL, SPANISH)),
            weekdayTotals.get(day)))
        .collect(Collectors.toList());
  }

  public List<MonthlyConfirmationDto> calculateMonthlyConfirmations(User user, int year) {
    List<MonthlyConfirmationDto> list = new ArrayList<>();

    for (int month = 1; month <= 12; month++) {
      Specification<Booking> confirmedSpec = Specification
          .where(BookingSpecifications.hasUserId(user.getId()))
          .and(BookingSpecifications.hasMonth(month))
          .and(BookingSpecifications.hasYear(year))
          .and(BookingSpecifications.hasConfirmationStatus(ConfirmationStatus.Confirmed));

      Specification<Booking> unconfirmedSpec = Specification
          .where(BookingSpecifications.hasUserId(user.getId()))
          .and(BookingSpecifications.hasMonth(month))
          .and(BookingSpecifications.hasYear(year))
          .and(BookingSpecifications.hasConfirmationStatus(ConfirmationStatus.Inactive)
              .or(BookingSpecifications.hasConfirmationStatus(ConfirmationStatus.Expired)));

      int confirmed = (int) bookingRepository.count(confirmedSpec);
      int unconfirmed = (int) bookingRepository.count(unconfirmedSpec);

      list.add(new MonthlyConfirmationDto(getMonthName(month), confirmed, unconfirmed));
    }

    return list;
  }

  private String getMonthName(int month) {
    String name = java.time.Month.of(month).getDisplayName(TextStyle.FULL, SPANISH);
    return capitalize(name);
  }

  private String capitalize(String text) {
    return text.substring(0, 1).toUpperCase() + text.substring(1);
  }
}