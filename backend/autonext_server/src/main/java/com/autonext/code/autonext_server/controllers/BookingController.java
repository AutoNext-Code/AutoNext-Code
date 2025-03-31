package com.autonext.code.autonext_server.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.autonext.code.autonext_server.dto.BookingDTO;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.services.BookingService;
import com.autonext.code.autonext_server.services.JwtService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final JwtService jwtService;

    public BookingController(BookingService bookingService, JwtService jwtService) {
        this.bookingService = bookingService;
        this.jwtService = jwtService;
    }

    @GetMapping("/user")
    @SecurityRequirement(name = "bearerAuth")
    public Page<BookingDTO> getBookingsByUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false, name = "delegation") String workCenter,
            @RequestParam(required = false) String carPlate) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();

        User user = (User) authentication.getPrincipal(); 
        int userId = user.getId();

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, 6, sort);

        Page<Booking> bookings = bookingService.getBookingsByUser(userId, pageable, date, workCenter, carPlate);

        List<BookingDTO> bookingDTOs = bookings.getContent().stream()
                .map(booking -> new BookingDTO(
                        booking.getDate(),
                        booking.getStartTime(),
                        booking.getEndTime(),
                        booking.getUser().getName(),
                        booking.getCar().getCarPlate(),
                        booking.getStatus().toString(),
                        booking.getParkingSpace() != null ? booking.getParkingSpace().getSpaceCode() : "N/A"))
                .collect(Collectors.toList());

        return new PageImpl<>(bookingDTOs, pageable, bookings.getTotalElements());
    }
}
