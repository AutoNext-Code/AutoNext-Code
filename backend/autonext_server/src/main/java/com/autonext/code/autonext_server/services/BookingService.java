package com.autonext.code.autonext_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.repositories.BookingRepository;

@Service
public class BookingService {

    @Autowired
    public BookingRepository bookingRepository;

    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    public Booking getBookingById(int id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(int id, Booking booking) {
        if (bookingRepository.existsById(id)) {
            booking.setId(id);
            return bookingRepository.save(booking);
        } else {
            return null;
        }
    }
}
