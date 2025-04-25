package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
import com.autonext.code.autonext_server.repositories.UserRepository;

@Service
public class StrikeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StrikeRepository strikeRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EmailTemplateService emailTemplateService;

    public void setBookingStrike(Booking booking, LocalDate date, LocalTime time, StrikeReason reason ){


        booking.setStatus(BookingStatus.Strike);
        bookingRepository.save(booking);
        User user = booking.getUser();


        Strike strike = new Strike(date, time, reason, booking.getUser());
        strikeRepository.save(strike);
       
        System.out.println(strikeRepository.findActiveStrikeByUser(user.getId()).size());
        if(strikeRepository.findActiveStrikeByUser(user.getId()).size()>2){
            
            user.setRole(Role.Penalized);
            userRepository.save(user);


            List<Booking> bookings = bookingRepository.findByUserAndConfirmationStatus(user, ConfirmationStatus.Inactive);
            bookings.forEach(b -> {b.setConfirmationStatus(ConfirmationStatus.Expired);b.setStatus(BookingStatus.Blocked);});
            bookingRepository.saveAll(bookings);
        }

    }
    
}
