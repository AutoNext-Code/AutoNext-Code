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
import org.springframework.http.HttpStatus;
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", filename);
        headers.setAccessControlExposeHeaders(List.of("Content-Disposition"));

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);

    }

    @GetMapping("/years")
    public List<Integer> getAvailableYears() {
        int currentYear = LocalDate.now().getYear();
        return IntStream.rangeClosed(2024, currentYear)
                .boxed()
                .collect(Collectors.toList());
    }
}