package com.autonext.code.autonext_server.configs.seeders;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.WorkCenter;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
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
import java.util.List;

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
        WorkCenterRepository workCenterRepository
    ) {
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

            User admin = userRepository.findByEmail("admin@example.com").orElseThrow();
            User user = userRepository.findByEmail("user@example.com").orElseThrow();

            Car adminCar = carRepository.findByUser(admin).get(0);
            Car userCar = carRepository.findByUser(user).get(0);

            ParkingSpace space1 = parkingSpaceRepository.findById(1).orElseThrow();
            ParkingSpace space2 = parkingSpaceRepository.findById(2).orElseThrow();

            WorkCenter center1 = workCenterRepository.findById(1).orElseThrow(); // Madrid
            WorkCenter center2 = workCenterRepository.findById(2).orElseThrow(); // Málaga

            Booking booking1 = new Booking(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalDate.now().plusDays(1),
                BookingStatus.Active,
                admin,
                adminCar
            );
            booking1.setParkingSpace(space1);
            booking1.setWorkCenter(center1);

            Booking booking2 = new Booking(
                LocalTime.of(12, 0),
                LocalTime.of(14, 0),
                LocalDate.now().plusDays(2),
                BookingStatus.Pending,
                user,
                userCar
            );
            booking2.setParkingSpace(space2);
            booking2.setWorkCenter(center2);

            bookingRepository.saveAll(List.of(booking1, booking2));
            System.out.println("Reservas creadas con éxito.");
        } else {
            System.out.println("La base de datos ya tiene reservas. Seeding skipped.");
        }
    }
}
