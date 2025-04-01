package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.BookingDTO;
import com.autonext.code.autonext_server.dto.MapBookingDTO;
import com.autonext.code.autonext_server.exceptions.BookingNotFoundException;
import com.autonext.code.autonext_server.exceptions.CarPlateNotExistsException;
import com.autonext.code.autonext_server.exceptions.ParkingSpaceNotExistsException;
import com.autonext.code.autonext_server.exceptions.ParkingSpaceOccupiedException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.mapper.BookingMapper;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.ParkingLevel;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.repositories.CarRepository;
import com.autonext.code.autonext_server.repositories.ParkingLevelRepository;
import com.autonext.code.autonext_server.repositories.ParkingSpaceRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;
import com.autonext.code.autonext_server.specifications.BookingSpecifications;

@Service
public class BookingService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;


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

    public List<Booking> getFilteredBookings(int userId, LocalDate date, String delegation, String carPlate) {
        Specification<Booking> spec = buildBookingFilter(userId, date, delegation, carPlate, null, null, null, null);
        return bookingRepository.findAll(spec);
    }

    public Page<Booking> getFilteredBookingsPaged(int userId, Pageable pageable,
            LocalDate date, String delegation, String carPlate,
            String plugType, String floor, String startTime, String endTime) {
        Specification<Booking> spec = buildBookingFilter(userId, date, delegation, carPlate, plugType, floor, startTime,
                endTime);
        return bookingRepository.findAll(spec, pageable);
    }

    public Booking getBookingById(int id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public void createBooking(MapBookingDTO mapBookingDTO) {
        
        Optional<Car> optionalCar = carRepository.findByCarPlate(mapBookingDTO.getCarPlate()) ;
        Optional<User> optionalUser = userRepository.findById(mapBookingDTO.getUserId()) ;
        Optional<ParkingSpace> optionalSpace = parkingSpaceRepository.findById(mapBookingDTO.getParkingSpaceId()) ;

        if (!optionalSpace.isPresent()) {
            throw new ParkingSpaceNotExistsException("La plaza no está registrada");
        }
        if (!optionalCar.isPresent()) {
            throw new CarPlateNotExistsException("La matrícula no está registrada");
        }        
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("Usuario No Encontrado") ;
        }


        ParkingSpace parkingSpace = optionalSpace.get() ;

        List<Booking> bookings = bookingRepository.findAllReservationsByDateAndSpace(mapBookingDTO.getDate(), parkingSpace) ;

        for (Booking booking : bookings) {

            boolean overlap = !(mapBookingDTO.getEndTime().isBefore(booking.getStartTime()) || 
                                mapBookingDTO.getStartTime().isAfter(booking.getEndTime()));

            if (overlap && booking.getStatus() != BookingStatus.Active) {
                throw new ParkingSpaceOccupiedException("La plaza está ocupada en el horario seleccionado");
            }
        }

        Car car = optionalCar.get() ;
        User user = optionalUser.get() ;

        Booking booking = new Booking(mapBookingDTO.getStartTime(), mapBookingDTO.getEndTime(), mapBookingDTO.getDate(), BookingStatus.Pending , user , car ) ;

        bookingRepository.save(booking) ;

    }

    public BookingDTO updateBookingState(int id, int userId, BookingStatus bookingStatus) throws Exception {
        Booking booking = bookingRepository.getReferenceById(id) ;

        try {

            if (booking == null) {
                throw new BookingNotFoundException("Reserva no encontrada") ;
            }
    
            if (booking.getUser().getId() != userId) {
                throw new UserNotFoundException("Usuario no encontrado") ;
            }
    
            booking.setStatus(bookingStatus);
            BookingDTO bookMap = BookingMapper.toDTO(booking) ;
            return bookMap;
            
        } catch (Exception e) {
            throw new Exception("Error desconocido") ;
        }
    }
}
