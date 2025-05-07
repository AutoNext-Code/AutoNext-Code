package com.autonext.code.autonext_server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.exceptions.ParkingSpaceNotExistsException;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.models.enums.JobPosition;
import com.autonext.code.autonext_server.models.enums.PlugType;
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

    public void updateSpaceData(int id, PlugType plugType, JobPosition jobPosition){
        System.out.println("\u001B[31m"+"Hola"+"\u001B[0m");
        
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(id)
            .orElseThrow(() -> new ParkingSpaceNotExistsException("Plaza no encontrada"));
        
        System.out.println("\u001B[31m"+"Hola"+"\u001B[0m");

        parkingSpace.setPlugType(plugType);
        parkingSpace.setJobPosition(jobPosition);

        List<Booking> listBookings = parkingSpace.getBookings() ;

        for (Booking booking : listBookings) {
            if (booking.getStatus() == BookingStatus.Pending) {
                booking.setStatus(BookingStatus.Cancelled) ;
            }
        }

        parkingSpace.setBookings(listBookings) ;
        

        parkingSpaceRepository.save(parkingSpace) ;

    }



    public Page<Booking> getBookingPage(Pageable pageable, int parkingSpaceId) {
        
        List<Booking> list= bookingRepository.findConfirmedPendingBySpace(parkingSpaceId ,BookingStatus.Active,  BookingStatus.Pending);

        return listToPage(list, pageable);
    }

    public Page<Booking> listToPage(List<Booking> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<Booking> sublist = list.subList(start, end);

        return new PageImpl<>(sublist, pageable, list.size());
    }


    
}
