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
            ParkingLevel madrid1 = parkingLevelRepository.findById(1).orElseThrow();
            ParkingLevel madrid2 = parkingLevelRepository.findById(2).orElseThrow();
            ParkingLevel madrid3 = parkingLevelRepository.findById(3).orElseThrow();
            ParkingLevel malaga0 = parkingLevelRepository.findById(4).orElseThrow();
            ParkingLevel malaga1 = parkingLevelRepository.findById(5).orElseThrow();

            List<ParkingSpace> spaces = new ArrayList<>();
            if(madrid1 !=null){
                //Madrid-1
                spaces.add(new ParkingSpace("30", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("60", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("90", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("125", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("155", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("190", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("225", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("255", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("290", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("325", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("355", "140", Direction.Down, PlugType.Schuko, ParkingState.Blocked, madrid1));
                spaces.add(new ParkingSpace("390", "140", Direction.Down, PlugType.Schuko, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("425", "140", Direction.Down, PlugType.Schuko, ParkingState.Blocked, madrid1));
                spaces.add(new ParkingSpace("455", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("490", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("525", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("555", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("585", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("620", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("650", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("690", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("720", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("750", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("790", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
    
                spaces.add(new ParkingSpace("155", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("190", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("225", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("255", "270", Direction.Up, PlugType.Type1, ParkingState.Blocked, madrid1));
                spaces.add(new ParkingSpace("290", "270", Direction.Up, PlugType.Type1, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("325", "270", Direction.Up, PlugType.Type1, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("355", "270", Direction.Up, PlugType.Type2, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("390", "270", Direction.Up, PlugType.Type2, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("425", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("455", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("490", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("525", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("555", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("585", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("620", "270", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
    
                spaces.add(new ParkingSpace("740", "260", Direction.Left, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("740", "290", Direction.Left, PlugType.CCS, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("740", "320", Direction.Left, PlugType.CCS, ParkingState.Blocked, madrid1));
                spaces.add(new ParkingSpace("740", "350", Direction.Left, PlugType.NoType, ParkingState.Unusable, madrid1));
    
                spaces.add(new ParkingSpace("155", "330", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("190", "330", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("225", "330", Direction.Down, PlugType.CHAdeMO, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("255", "330", Direction.Down, PlugType.CHAdeMO, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("290", "330", Direction.Down, PlugType.CHAdeMO, ParkingState.Blocked, madrid1));
                spaces.add(new ParkingSpace("325", "330", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("355", "330", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                
                System.out.println("Madrid-01 places seeder used");
            }else{
                System.out.println("Madrid-01 not found, spaces seeder for this plant is canceled");
            }    


            if(madrid2 !=null){
                //Madrid-2
                spaces.add(new ParkingSpace("180", "160", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("210", "160", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("245", "160", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("275", "160", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("310", "160", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("340", "160", Direction.Down, PlugType.Schuko, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("375", "160", Direction.Down, PlugType.Schuko, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("405", "160", Direction.Down, PlugType.Schuko, ParkingState.Blocked, madrid2));
                spaces.add(new ParkingSpace("445", "160", Direction.Down, PlugType.CCS, ParkingState.Blocked, madrid2));
                spaces.add(new ParkingSpace("475", "160", Direction.Down, PlugType.CCS, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("510", "160", Direction.Down, PlugType.CHAdeMO, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("540", "160", Direction.Down, PlugType.CHAdeMO, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("575", "160", Direction.Down, PlugType.Type1, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("605", "160", Direction.Down, PlugType.Type1, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("640", "160", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("670", "160", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("130", "230", Direction.Right, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("130", "260", Direction.Right, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("130", "295", Direction.Right, PlugType.Type2, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("130", "325", Direction.Right, PlugType.Type2, ParkingState.Blocked, madrid2));
                spaces.add(new ParkingSpace("130", "360", Direction.Right, PlugType.Schuko, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("130", "390", Direction.Right, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("275", "310", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("310", "310", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("375", "310", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("405", "310", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("507", "310", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("535", "310", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("575", "310", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("605", "310", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("180", "460", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("210", "460", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("245", "460", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("275", "460", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("310", "460", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("340", "460", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("375", "460", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("405", "460", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("445", "460", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("475", "460", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("510", "460", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("540", "460", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));

                System.out.println("Madrid-02 places seeder used");
            }else{
                System.out.println("Madrid-02 not found, spaces seeder for this plant is canceled");
            }


            if(madrid3 !=null){
                spaces.add(new ParkingSpace("135", "130", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("170", "130", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("203", "130", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                
                spaces.add(new ParkingSpace("240", "130", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("275", "130", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("304", "130", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                
                spaces.add(new ParkingSpace("340", "130", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("375", "130", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                
                // Centro Izquierda Arriba
                spaces.add(new ParkingSpace("135", "275", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("170", "275", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("215", "275", Direction.Up, PlugType.CCS, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("250", "275", Direction.Up, PlugType.CCS, ParkingState.Blocked, madrid3));
                spaces.add(new ParkingSpace("315", "275", Direction.Up, PlugType.CHAdeMO, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("345", "275", Direction.Up, PlugType.Type1, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("385", "275", Direction.Up, PlugType.Type2, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("420", "275", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                
                // Centro Izquierda Abajo
                spaces.add(new ParkingSpace("135", "345", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("170", "345", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("385", "345", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("420", "345", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                
                // Centro Derecha
                spaces.add(new ParkingSpace("550", "265", Direction.Left, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("550", "295", Direction.Left, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("540", "345", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("575", "345", Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                
                // Abajo
                spaces.add(new ParkingSpace("135", "500", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("170", "500", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("215", "500", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("250", "500", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("310", "500", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("345", "500", Direction.Up, PlugType.Type1, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("385", "500", Direction.Up, PlugType.Type1, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("420", "500", Direction.Up, PlugType.Type2, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("455", "500", Direction.Up, PlugType.Type2, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("495", "500", Direction.Up, PlugType.Schuko, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("540", "500", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("575", "500", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("615", "500", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("655", "500", Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
               
                System.out.println("Madrid-03 places seeder used");
            }else{
                System.out.println("Madrid-03 not found, spaces seeder for this plant is canceled");
            }

            if(malaga0 !=null){

                spaces.add(new ParkingSpace("160", "200", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("201", "200", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("239", "200", Direction.Down, PlugType.CCS, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("277", "200", Direction.Down, PlugType.CCS, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("315", "200", Direction.Down, PlugType.CCS, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("355", "200", Direction.Down, PlugType.CCS, ParkingState.Occupied, malaga0));
                spaces.add(new ParkingSpace("394", "200", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("433", "200", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));

                spaces.add(new ParkingSpace("586", "200", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("625", "200", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("665", "200", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));

                /* Abajo */

                spaces.add(new ParkingSpace("160", "370", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("201", "370", Direction.Up, PlugType.CHAdeMO, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("239", "370", Direction.Up, PlugType.CHAdeMO, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("277", "370", Direction.Up, PlugType.CHAdeMO, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("315", "370", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("355", "370", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga0));

                spaces.add(new ParkingSpace("467", "370", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("506", "370", Direction.Up, PlugType.Type1, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("546", "370", Direction.Up, PlugType.Type2, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("586", "370", Direction.Up, PlugType.Schuko, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("625", "370", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("665", "370", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga0));



                System.out.println("Málaga-00 places seeder used");
            }else{
                System.out.println("Málaga-00 not found, spaces seeder for this plant is canceled");
            }


            if(malaga1 !=null){

                /* Arriba */
                spaces.add(new ParkingSpace("75", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("110", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("147", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("185", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("222", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("256", "140", Direction.Down, PlugType.Schuko, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("293", "140", Direction.Down, PlugType.Schuko, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("330", "140", Direction.Down, PlugType.Schuko, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("365", "140", Direction.Down, PlugType.Schuko, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("400", "140", Direction.Down, PlugType.Type1, ParkingState.Blocked, malaga1));
                spaces.add(new ParkingSpace("437", "140", Direction.Down, PlugType.Type1, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("474", "140", Direction.Down, PlugType.Type2, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("510", "140", Direction.Down, PlugType.CCS, ParkingState.Blocked, malaga1));
                spaces.add(new ParkingSpace("545", "140", Direction.Down, PlugType.CCS, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("583", "140", Direction.Down, PlugType.CHAdeMO, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("617", "140", Direction.Down, PlugType.CHAdeMO, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("655", "140", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));

                /* Centro Arriba */
                spaces.add(new ParkingSpace("75", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("110", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("147", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("185", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("222", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("256", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("293", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("330", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("365", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("400", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("437", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("474", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("510", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("545", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("583", "280", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));

                /* Centro Abajo */
                spaces.add(new ParkingSpace("75", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("110", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("147", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("185", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("222", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("256", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("293", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("330", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("365", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("400", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("437", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("474", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("510", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("545", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("583", "350", Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));

                /* Abajo */
                spaces.add(new ParkingSpace("110", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("147", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("185", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("222", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("260", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("297", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("333", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("370", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("407", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("444", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("480", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("517", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("552", "490", Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));



                System.out.println("Málaga-01 places seeder used");
            }else{
                System.out.println("Málaga-01 not found, spaces seeder for this plant is canceled");
            }

            parkingSpaceRepository.saveAll(spaces);
            System.out.println("Plazas creadas con éxito");

        }else{
            System.out.println("La base de datos ya tiene plazas asignadas. Seeding skipped");
        }
    }
    
}
