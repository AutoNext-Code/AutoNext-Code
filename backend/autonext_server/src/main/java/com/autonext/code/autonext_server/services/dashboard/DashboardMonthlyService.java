package com.autonext.code.autonext_server.services.dashboard;

import com.autonext.code.autonext_server.dto.dashboardDtos.MonthlyMetricDto;
import com.autonext.code.autonext_server.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class DashboardMonthlyService {

    private static final Locale SPANISH = Locale.forLanguageTag("es-ES");

    @Autowired
    private DashboardKpiService dashboardKpiService;

    public List<MonthlyMetricDto> calculateMonthlyDaysReserved(User user, int year) {
        List<MonthlyMetricDto> list = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            int value = dashboardKpiService.calculateTotalDaysReserved(user, month, year);
            list.add(new MonthlyMetricDto(getMonthName(month), value));
        }
        return list;
    }

    public List<MonthlyMetricDto> calculateMonthlyHoursReserved(User user, int year) {
        List<MonthlyMetricDto> list = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            int value = dashboardKpiService.calculateTotalHoursReserved(user, month, year);
            list.add(new MonthlyMetricDto(getMonthName(month), value));
        }
        return list;
    }

    public List<MonthlyMetricDto> calculateMonthlyAvgDuration(User user, int year) {
        List<MonthlyMetricDto> list = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            double avg = dashboardKpiService.calculateAverageSessionDuration(user, month, year);
            list.add(new MonthlyMetricDto(getMonthName(month), (int) Math.round(avg)));
        }
        return list;
    }

    private String getMonthName(int month) {
        String name = Month.of(month).getDisplayName(TextStyle.FULL, SPANISH);
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}