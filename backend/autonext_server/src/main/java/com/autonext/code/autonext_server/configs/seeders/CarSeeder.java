package com.autonext.code.autonext_server.configs.seeders;

import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.PlugType;
import com.autonext.code.autonext_server.repositories.CarRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(5)
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
            User prueba = userRepository.findByEmail("prueba@example.com").orElseThrow();
            User penalized = userRepository.findByEmail("penalized@example.com").orElseThrow();

            if (admin != null && user != null && prueba != null && penalized != null) {
                Car carAdmin = new Car("0000AAA", "Coche 1", PlugType.Undefined, admin);
                Car carUser = new Car("0000BBB", "Coche 1", PlugType.Undefined, user);
                Car carUser2 = new Car("11111BB", "Coche 2", PlugType.Type1, user);
                Car carPrueba = new Car("0000CCC", "Coche 1", PlugType.Undefined, prueba);
                Car carPenalized = new Car("0000DDD", "Coche 1", PlugType.Undefined, penalized);

                carRepository.saveAll(List.of(carAdmin, carUser, carPrueba, carUser2, carPenalized));
                System.out.println("Coches creados con éxito");
            } else {
                System.out.println("No se encontraron todos los usuarios para asignar coches");
            }
        } else {
            System.out.println("La base de datos ya tiene coches. Seeding skipped.");
        }
    }
}
