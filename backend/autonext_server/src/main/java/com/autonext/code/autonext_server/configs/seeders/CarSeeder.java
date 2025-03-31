package com.autonext.code.autonext_server.configs.seeders;

import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.PlugType;
import com.autonext.code.autonext_server.repositories.CarRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarSeeder implements CommandLineRunner {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public CarSeeder(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (carRepository.count() == 0) {
            User admin = userRepository.findByEmail("admin@example.com").orElseThrow();
            User user = userRepository.findByEmail("user@example.com").orElseThrow();

            if (admin != null && user != null) {
                Car carAdmin = new Car("0000AAA", "Coche 1", PlugType.Undefined, admin);
                Car carUser = new Car("0000BBB", "Coche 1", PlugType.Undefined, user);

                carRepository.saveAll(List.of(carAdmin, carUser));
                System.out.println("Coches creados con éxito");
            } else {
                System.out.println("No se encontraron usuarios para asignar coches");
            }
        } else {
            System.out.println("La base de datos ya tiene coches. Seeding skipped.");
        }
    }
}