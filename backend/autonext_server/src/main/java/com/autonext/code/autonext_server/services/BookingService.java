package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.specifications.BookingSpecifications;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    private Specification<Booking> buildBookingFilter(int userId, LocalDate date, String delegation, String carPlate,
            String plugType, String floor, String startTime, String endTime) {
        return Specification.where(BookingSpecifications.hasUserId(userId))
                .and(BookingSpecifications.hasDate(date))
                .and(BookingSpecifications.hasDelegation(delegation))
                .and(BookingSpecifications.hasCarPlate(carPlate))
                .and(BookingSpecifications.hasPlugType(plugType))
                .and(BookingSpecifications.hasFloor(floor))
                .and(BookingSpecifications.hasStartTime(startTime))
                .and(BookingSpecifications.hasEndTime(endTime));
    }

    public List<Booking> getFilteredBookings(int userId,
            LocalDate date,
            String delegation,
            String carPlate,
            String plugType,
            String floor,
            String startTime,
            String endTime) {
        Specification<Booking> spec = buildBookingFilter(userId, date, delegation, carPlate, plugType, floor, startTime,
                endTime);
        return bookingRepository.findAll(spec);
    }

    public Page<Booking> getFilteredBookingsPaged(int userId,
            Pageable pageable,
            LocalDate date,
            String delegation,
            String carPlate) {
        Specification<Booking> spec = buildBookingFilter(userId, date, delegation, carPlate, null, null, null, null);
        return bookingRepository.findAll(spec, pageable);
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
