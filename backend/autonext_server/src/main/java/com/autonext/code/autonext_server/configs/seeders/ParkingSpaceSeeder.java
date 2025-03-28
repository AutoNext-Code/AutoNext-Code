package com.autonext.code.autonext_server.configs.seeders;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.autonext.code.autonext_server.models.ParkingLevel;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.repositories.ParkingLevelRepository;
import com.autonext.code.autonext_server.repositories.ParkingSpaceRepository;

@Order(4)
@Component
public class ParkingSpaceSeeder implements CommandLineRunner{

    private final ParkingLevelRepository parkingLevelRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;

    public ParkingSpaceSeeder(ParkingLevelRepository parkingLevelRepository, ParkingSpaceRepository parkingSpaceRepository){
        this.parkingLevelRepository = parkingLevelRepository;
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    @Override
    public void run(String... args){
        if(parkingLevelRepository.count() == 0){
            ParkingLevel level = parkingLevelRepository.findById(1).orElseThrow();


            if(level !=null){
                ParkingSpace plaza1 = new ParkingSpace(null, null, null, null, null);

                parkingSpaceRepository.saveAll(List.of(plaza1));
                System.out.println("Plazas creadas con éxito");
            }else{
                System.out.println("No se encontraron plantas a las que asignar plazas");
            }
        }else{
            System.out.println("La base de datos ya tiene plazas asignadas. Seeding skipped");
        }
    }
    
}
