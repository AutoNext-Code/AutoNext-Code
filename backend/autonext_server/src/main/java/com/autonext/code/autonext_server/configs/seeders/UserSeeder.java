package com.autonext.code.autonext_server.configs.seeders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.BookingStatus;
import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.PlugType;
import com.autonext.code.autonext_server.models.Role;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.repositories.CarRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;

@Component
public class UserSeeder implements CommandLineRunner {

    private final BookingRepository bookingRepository;

  private final UserRepository userRepository;
  private final CarRepository carRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserSeeder(UserRepository userRepository, CarRepository carRepository, BookingRepository bookingRepository) {
    this.userRepository = userRepository;
    this.carRepository = carRepository;
    this.bookingRepository = bookingRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  @Override
  public void run(String... args) {
      if (userRepository.count() == 0) { 
          User admin = new User("admin@example.com", "Admin", "User", passwordEncoder.encode("admin123"), true);
          admin.setJobPosition("Web administrator");
          admin.setRole(Role.Admin);
  
          User user = new User("user@example.com", "User", "User", passwordEncoder.encode("user123"), true);
          user.setJobPosition("Developer");
          user.setRole(Role.User);
  
          userRepository.saveAll(List.of(admin, user));
  
          Car carAdmin = new Car("0000AAA", "Coche 1", PlugType.Undefined, admin);
  
          Car carUser = new Car("0000BBB", "Coche 1", PlugType.Undefined, user);

          
          carRepository.saveAll(List.of(carAdmin, carUser));
          
          Booking booking1 = new Booking(LocalTime.of(9, 0), LocalTime.of(11, 0), LocalDate.now(), BookingStatus.Pending, admin, carAdmin);
          Booking booking2 = new Booking(LocalTime.of(14, 0), LocalTime.of(16, 0), LocalDate.now().plusDays(1), BookingStatus.Pending, user, carUser);

          bookingRepository.saveAll(List.of(booking1, booking2));
          System.out.println("Database seeded successfully! (Admin & User with Cars created)");
      } else {
          System.out.println("Database already has users. Seeding skipped.");
      }
  }
  
}