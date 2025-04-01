package com.autonext.code.autonext_server.mapper;

import com.autonext.code.autonext_server.dto.BookingDTO;
import com.autonext.code.autonext_server.models.Booking;

public class BookingMapper {

    public static BookingDTO toDTO(Booking booking) {
        if (booking == null) return null;

        return new BookingDTO(
            booking.getDate(),
            booking.getStartTime(),
            booking.getEndTime(),
            booking.getUser().getName() + " " + booking.getUser().getSurname(),
            booking.getCar().getCarPlate(),
            booking.getStatus().name()
        );
    }
}
