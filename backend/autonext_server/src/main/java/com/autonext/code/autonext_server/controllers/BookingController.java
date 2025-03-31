package com.autonext.code.autonext_server.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.dto.BookingDTO;
import com.autonext.code.autonext_server.mapper.BookingMapper;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.services.BookingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public Page<BookingDTO> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "userName") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<Booking> bookings = bookingService.getAllBookings(pageable);

        List<BookingDTO> bookingDTOs = bookings.getContent().stream()
            .map(BookingMapper::toDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(bookingDTOs, pageable, bookings.getTotalElements());
    }


    @PostMapping
    public BookingDTO createBooking(@RequestParam Booking booking) {
        Booking createdBooking = bookingService.createBooking(booking);
        return BookingMapper.toDTO(createdBooking);
    }

    @PutMapping("/{id}")
    public BookingDTO updateBooking(@RequestParam int id, @RequestParam Booking booking) {
        Booking updatedBooking = bookingService.updateBooking(id, booking);
        return BookingMapper.toDTO(updatedBooking);
    }

}
