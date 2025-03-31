package com.autonext.code.autonext_server.configs.seeders;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.autonext.code.autonext_server.models.ParkingLevel;
import com.autonext.code.autonext_server.models.WorkCenter;
import com.autonext.code.autonext_server.repositories.ParkingLevelRepository;
import com.autonext.code.autonext_server.repositories.WorkCenterRepository;

@Order(3)
@Component
public class ParkingLevelSeeder implements CommandLineRunner{
    private  final ParkingLevelRepository parkingLevelRepository;
    private final WorkCenterRepository workCenterRepository;

    public ParkingLevelSeeder(ParkingLevelRepository parkingLevelRepository, WorkCenterRepository workCenterRepository){
        this.parkingLevelRepository = parkingLevelRepository;
        this.workCenterRepository = workCenterRepository;
    }

    @Override
    public void run(String... args) {
        if(parkingLevelRepository.count() == 0){
            WorkCenter center1 = workCenterRepository.findByName("Madrid").orElseThrow();
            WorkCenter center2 = workCenterRepository.findByName("Málaga").orElseThrow();


            if(center1 != null && center2 != null){
                ParkingLevel madrid1 = new ParkingLevel("1", center1);
                ParkingLevel madrid2 = new ParkingLevel("2", center1);
                ParkingLevel madrid3 = new ParkingLevel("3", center1);
                ParkingLevel malaga0 = new ParkingLevel("0", center2);
                ParkingLevel malaga1 = new ParkingLevel("1", center2);

                parkingLevelRepository.saveAll(List.of(madrid1,madrid2,madrid3,malaga0,malaga1));
                System.out.println("Plantas creadas con éxito");
            }else{
                System.out.println("No se encontraron las sedes para asignar las plantas");
            }
        }else{
            System.out.println("La base de datos ya tiene plantas. Seeding skiped");
        }
    }
    
}
