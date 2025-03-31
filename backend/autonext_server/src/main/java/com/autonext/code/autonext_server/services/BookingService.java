package com.autonext.code.autonext_server.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.repositories.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Page<Booking> getBookingsByUser(int userId, Pageable pageable, LocalDate date, String delegation, String carPlate) {
        return bookingRepository.findByFilters(userId, date, delegation, carPlate, pageable);
    }
}