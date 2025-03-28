package com.autonext.code.autonext_server.configs.seeders;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.autonext.code.autonext_server.models.WorkCenter;
import com.autonext.code.autonext_server.repositories.WorkCenterRepository;

@Order(2)
@Component
public class WorkCenterSeeder implements CommandLineRunner{

    private final WorkCenterRepository workCenterRepository;

    public WorkCenterSeeder(WorkCenterRepository workCenterRepository){
        this.workCenterRepository = workCenterRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("creando sedes");
        if (workCenterRepository.count() == 0){
            WorkCenter center1 = new WorkCenter("Madrid");
            WorkCenter center2 = new WorkCenter("Málaga");

            workCenterRepository.saveAll(List.of(center1, center2));
            System.out.println("Sedes creadas con éxito");
        }else {
            System.out.println("La base de datos ya tiene sedes. Seeding skipped");
        }
    }

    
    
}
