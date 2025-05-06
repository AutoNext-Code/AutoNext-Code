package com.autonext.code.autonext_server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.exceptions.ParkingSpaceNotExistsException;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.repositories.ParkingSpaceRepository;

@Service
public class SpaceService {
    
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    private BookingRepository bookingRepository;


    public void updateActiveStatus(int id, boolean blocked){
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(id)
        .orElseThrow(() -> new ParkingSpaceNotExistsException("Plaza no encontrada"));

        if(blocked!=parkingSpace.isBlocked()){

            if(blocked){
                List<Booking> bookings = bookingRepository.findByParkingSpaceAndConfirmationStatus(parkingSpace, ConfirmationStatus.Inactive);
                bookings.forEach(b -> {b.setConfirmationStatus(ConfirmationStatus.Expired);b.setStatus(BookingStatus.Cancelled);});
                bookingRepository.saveAll(bookings);
            }

            parkingSpace.setBlocked(blocked);
            parkingSpaceRepository.save(parkingSpace);

        }

    }
}
