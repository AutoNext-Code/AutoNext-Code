package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.Strike;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.models.enums.Role;
import com.autonext.code.autonext_server.models.enums.StrikeReason;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.repositories.StrikeRepository;

@Service
public class StrikeService {

    @Autowired
    private StrikeRepository strikeRepository;

    @Autowired
    private BookingRepository bookingRepository;


    public void setBookingStrike(Booking booking, LocalDate date, LocalTime time, StrikeReason reason ){


        booking.setStatus(BookingStatus.Strike);
        bookingRepository.save(booking);
        User user = booking.getUser();


        Strike strike = new Strike(date, time, reason, booking.getUser());
        strikeRepository.save(strike);
       

        if(strikeRepository.findActiveStrikeByUser(user.getId()).size()>2){

            user.setRole(Role.Penalized);

            bookingRepository.UpdateStatusByUser(user, ConfirmationStatus.Inactive, ConfirmationStatus.PendingConfirmation);
        }

    }
    
}
