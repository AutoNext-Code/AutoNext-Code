package com.autonext.code.autonext_server.configs.seeders;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import com.autonext.code.autonext_server.models.WorkCenter;
import com.autonext.code.autonext_server.repositories.WorkCenterRepository;

@Component
public class WorkCenterSeeder implements CommandLineRunner{

    private final WorkCenterRepository workRepository;


    public WorkCenterSeeder(WorkCenterRepository workRepository) {
        this.workRepository = workRepository;

    }


    @Override
    public void run(String... args) {
    if (workRepository.count() == 0) { 

        //Crear WorkCenters
        WorkCenter centro1 = new WorkCenter("Malaga", "GreenRay");
        WorkCenter centro2 = new WorkCenter("Madrid", "BlueRay");

        // Guarda en la BD
        workRepository.saveAll(List.of(centro1, centro2));

      System.out.println("Database seeded successfully! (Admin & User created)");
    } else {
      System.out.println("Database already has users. Seeding skipped.");
    }
  }

}
