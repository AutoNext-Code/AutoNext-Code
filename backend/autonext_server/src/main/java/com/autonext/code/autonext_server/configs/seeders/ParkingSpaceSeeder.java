package com.autonext.code.autonext_server.configs.seeders;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.autonext.code.autonext_server.models.ParkingLevel;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.enums.Direction;
import com.autonext.code.autonext_server.models.enums.ParkingState;
import com.autonext.code.autonext_server.models.enums.PlugType;
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
        if(parkingSpaceRepository.count() == 0){
            ParkingLevel level = parkingLevelRepository.findById(1).orElseThrow();


            if(level !=null){
                List<ParkingSpace> spaces = new ArrayList<>(List.of(
                    new ParkingSpace("30", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("60", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("90", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("125", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("155", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("190", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("225", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("255", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("290", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("325", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("355", "140", Direction.Down, PlugType.Schuko, ParkingState.Blocked, level),
                    new ParkingSpace("390", "140", Direction.Down, PlugType.Schuko, ParkingState.Available, level),
                    new ParkingSpace("425", "140", Direction.Down, PlugType.Schuko, ParkingState.Blocked, level),
                    new ParkingSpace("455", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("490", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("525", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("555", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("585", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("620", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("650", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("690", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("720", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("750", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("790", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
        
                    new ParkingSpace("155", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("190", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("225", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("255", "270", Direction.Up, PlugType.Type1, ParkingState.Blocked, level),
                    new ParkingSpace("290", "270", Direction.Up, PlugType.Type1, ParkingState.Available, level),
                    new ParkingSpace("325", "270", Direction.Up, PlugType.Type1, ParkingState.Occupied, level),
                    new ParkingSpace("355", "270", Direction.Up, PlugType.Type2, ParkingState.Occupied, level),
                    new ParkingSpace("390", "270", Direction.Up, PlugType.Type2, ParkingState.Available, level),
                    new ParkingSpace("425", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("455", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("490", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("525", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("555", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("585", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("620", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, level),
        
                    new ParkingSpace("740", "260", Direction.Left, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("740", "290", Direction.Left, PlugType.CCS, ParkingState.Occupied, level),
                    new ParkingSpace("740", "320", Direction.Left, PlugType.CCS, ParkingState.Blocked, level),
                    new ParkingSpace("740", "350", Direction.Left, PlugType.NoType, ParkingState.Unusable, level),
        
                    new ParkingSpace("155", "330", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("190", "330", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("225", "330", Direction.Down, PlugType.CHAdeMO, ParkingState.Available, level),
                    new ParkingSpace("255", "330", Direction.Down, PlugType.CHAdeMO, ParkingState.Occupied, level),
                    new ParkingSpace("290", "330", Direction.Down, PlugType.CHAdeMO, ParkingState.Blocked, level),
                    new ParkingSpace("325", "330", Direction.Down, PlugType.NoType, ParkingState.Unusable, level),
                    new ParkingSpace("355", "330", Direction.Down, PlugType.NoType, ParkingState.Unusable, level)
                ));
                

                parkingSpaceRepository.saveAll(spaces);
                System.out.println("Plazas creadas con éxito");
            }else{
                System.out.println("No se encontraron plantas a las que asignar plazas");
            }
        }else{
            System.out.println("La base de datos ya tiene plazas asignadas. Seeding skipped");
        }
    }
    
}
