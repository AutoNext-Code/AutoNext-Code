package com.autonext.code.autonext_server.configs.seeders;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.WorkCenter;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.models.enums.ConfirmationStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.repositories.CarRepository;
import com.autonext.code.autonext_server.repositories.ParkingSpaceRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;
import com.autonext.code.autonext_server.repositories.WorkCenterRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Order(6)
@Component
public class BookingSeeder implements CommandLineRunner {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final WorkCenterRepository workCenterRepository;

    public BookingSeeder(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            CarRepository carRepository,
            ParkingSpaceRepository parkingSpaceRepository,
            WorkCenterRepository workCenterRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.parkingSpaceRepository = parkingSpaceRepository;
        this.workCenterRepository = workCenterRepository;
    }

    @Override
    public void run(String... args) {
        if (bookingRepository.count() == 0) {
            System.out.println("Creando bookings...");
    
            User user = userRepository.findByEmail("user@example.com").orElseThrow();
            Car userCar = carRepository.findByUser(user).get(0);
    
            ParkingSpace space1 = parkingSpaceRepository.findById(12).orElseThrow();
            ParkingSpace space2 = parkingSpaceRepository.findById(2).orElseThrow();
    
            WorkCenter center1 = workCenterRepository.findById(1).orElseThrow(); 
            WorkCenter center2 = workCenterRepository.findById(2).orElseThrow(); 
    
            for (int i = 0; i < 100; i++) {
                LocalDate bookingDate = LocalDate.of(2024, 1, 1).plusDays(i * 7); 

                Booking booking = new Booking(
                        LocalTime.of(9 + i % 5, 0), 
                        LocalTime.of(11 + i % 5, 0),
                        bookingDate, 
                        user,
                        userCar);
                booking.setParkingSpace(i % 2 == 0 ? space1 : space2); 
                booking.setWorkCenter(i % 2 == 0 ? center1 : center2); 
    
                if (bookingDate.isBefore(LocalDate.now())) {
                    booking.setStatus(BookingStatus.Completed);
                    booking.setConfirmationStatus(ConfirmationStatus.Confirmed);
                } else if (i % 3 == 0) {
                    booking.setStatus(BookingStatus.Strike);
                    booking.setConfirmationStatus(ConfirmationStatus.Expired);
                } else {
                    booking.setStatus(BookingStatus.Pending);
                    booking.setConfirmationStatus(ConfirmationStatus.PendingConfirmation);
                }
    
                bookingRepository.save(booking);
            }
    
            System.out.println("Reservas creadas con éxito.");
        } else {
            System.out.println("La base de datos ya tiene reservas. Seeding skipped.");
        }
    }
}
