package com.autonext.code.autonext_server.services.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.dashboardDtos.DashboardSummaryDto;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.Strike;
import com.autonext.code.autonext_server.repositories.UserRepository;

@Service
public class DashboardService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private DashboardKpiService dashboardKpiService;

  @Autowired
  private DashboardMonthlyService dashboardMonthlyService;

  @Autowired
  private DashboardStatsService dashboardStatsService;

  public DashboardSummaryDto getDashboardForCurrentUser(int month, int year) {
    int userId = getAuthenticatedUserId();

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    DashboardSummaryDto dto = new DashboardSummaryDto();

    // KPIs
    dto.setTotalDaysReserved(dashboardKpiService.calculateTotalDaysReserved(user, month, year));
    dto.setTotalHoursReserved(dashboardKpiService.calculateTotalHoursReserved(user, month, year));
    dto.setAverageSessionDuration(dashboardKpiService.calculateAverageSessionDuration(user, month, year));
    dto.setConfirmedReservations(dashboardKpiService.calculateConfirmedReservations(user, month, year));
    dto.setUnconfirmedReservations(dashboardKpiService.calculateUnconfirmedReservations(user, month, year));

    dto.setMonthlyDaysReserved(dashboardMonthlyService.calculateMonthlyDaysReserved(user, year));
    dto.setMonthlyHoursReserved(dashboardMonthlyService.calculateMonthlyHoursReserved(user, year));
    dto.setMonthlyAvgDuration(dashboardMonthlyService.calculateMonthlyAvgDuration(user, year));

    dto.setWeeklyHoursReserved(dashboardStatsService.calculateWeekdayHoursReserved(user, year));
    dto.setMonthlyConfirmationStats(dashboardStatsService.calculateMonthlyConfirmations(user, year));

    // Strikes
    int activeStrikes = (int) user.getStrikes().stream()
        .filter(Strike::isActive)
        .count();
    dto.setStrikes(activeStrikes);
    dto.setBanned(user.isBanned());

    return dto;
  }

  private int getAuthenticatedUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();

    if (principal instanceof User user) {
      return user.getId();
    }

    throw new SecurityException("Usuario no autenticado correctamente");
  }
}