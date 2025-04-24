package com.autonext.code.autonext_server.controllers;

import com.autonext.code.autonext_server.dto.dashboardDtos.DashboardExportRequest;
import com.autonext.code.autonext_server.dto.dashboardDtos.DashboardSummaryDto;
import com.autonext.code.autonext_server.services.dashboard.DashboardPdfService;
import com.autonext.code.autonext_server.services.dashboard.DashboardService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private DashboardPdfService dashboardPdfService;

    @GetMapping
    public DashboardSummaryDto getDashboard(
            @RequestParam(required = false) Integer month,
            @RequestParam int year) {
        return dashboardService.getDashboardForCurrentUser(month, year);
    }

    @PostMapping("/export")
    public ResponseEntity<byte[]> exportDashboardToPdf(@RequestBody DashboardExportRequest request) {
        byte[] pdf = dashboardPdfService.generatePdf(request);

        String filename = (request.getMonth() != null)
                ? String.format("dashboard_%02d-%d.pdf", request.getMonth(), request.getYear())
                : String.format("dashboard_%d.pdf", request.getYear());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(pdf);
    }

    @GetMapping("/years")
    public List<Integer> getAvailableYears() {
        // Ejemplo simple: del 2023 al año actual
        int currentYear = LocalDate.now().getYear();
        return IntStream.rangeClosed(2023, currentYear)
                .boxed()
                .collect(Collectors.toList());
    }
}