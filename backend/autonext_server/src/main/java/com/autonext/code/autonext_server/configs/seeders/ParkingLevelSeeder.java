package com.autonext.code.autonext_server.configs.seeders;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.autonext.code.autonext_server.models.ParkingLevel;
import com.autonext.code.autonext_server.repositories.ParkingLevelRepository;


@Component
public class ParkingLevelSeeder implements CommandLineRunner{


    private final ParkingLevelRepository levelRepository;


    public ParkingLevelSeeder(ParkingLevelRepository levelRepository) {
        this.levelRepository = levelRepository;

    }


    @Override
    public void run(String... args) {
    if (levelRepository.count() == 0) { 

        //Crear WorkCenters
        ParkingLevel planta1 = new ParkingLevel(0, null);
        //Retocar los center y buscar como asignarlos


        // Guarda en la BD
        levelRepository.saveAll(List.of(planta1));

      System.out.println("Database seeded successfully! (Admin & User created)");
    } else {
      System.out.println("Database already has users. Seeding skipped.");
    }
  }


}
