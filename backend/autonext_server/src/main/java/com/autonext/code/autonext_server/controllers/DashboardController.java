package com.autonext.code.autonext_server.controllers;

import com.autonext.code.autonext_server.dto.dashboardDtos.DashboardSummaryDto;
import com.autonext.code.autonext_server.services.dashboard.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public DashboardSummaryDto getDashboard(
        @RequestParam int month,
        @RequestParam int year
    ) {
        return dashboardService.getDashboardForCurrentUser(month, year);
    }
}