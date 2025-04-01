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
                spaces.add(new ParkingSpace( "MAD1-1", 30, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-2", 60, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-3", 90, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-4", 125, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-5", 155, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-6", 190, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-7", 225, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-8", 255, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-9", 290, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-10", 325, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-11", 355, 140, Direction.Down, PlugType.Schuko, ParkingState.Blocked, madrid1));
                spaces.add(new ParkingSpace( "MAD1-12", 390, 140, Direction.Down, PlugType.Schuko, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace( "MAD1-13", 425, 140, Direction.Down, PlugType.Schuko, ParkingState.Blocked, madrid1));
                spaces.add(new ParkingSpace( "MAD1-14", 455, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-15", 490, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-16", 525, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-17", 555, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-18", 585, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-19", 620, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-20", 650, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-21", 690, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-22", 720, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-23", 750, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace( "MAD1-24", 790, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
    
                spaces.add(new ParkingSpace("MAD1-25", 155, 270, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-26", 190, 270, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-27", 225, 270, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-28", 255, 270, Direction.Up, PlugType.Type1, ParkingState.Blocked, madrid1));
                spaces.add(new ParkingSpace("MAD1-29", 290, 270, Direction.Up, PlugType.Type1, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("MAD1-30", 325, 270, Direction.Up, PlugType.Type1, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("MAD1-31", 355, 270, Direction.Up, PlugType.Type2, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("MAD1-32", 390, 270, Direction.Up, PlugType.Type2, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("MAD1-33", 425, 270, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-34", 455, 270, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-35", 490, 270, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-36", 525, 270, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-37", 555, 270, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-38", 585, 270, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-39", 620, 270, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid1));
    
                spaces.add(new ParkingSpace("MAD1-40", 740, 260, Direction.Left, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-41", 740, 290, Direction.Left, PlugType.CCS, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("MAD1-42", 740, 320, Direction.Left, PlugType.CCS, ParkingState.Blocked, madrid1));
                spaces.add(new ParkingSpace("MAD1-43", 740, 350, Direction.Left, PlugType.NoType, ParkingState.Unusable, madrid1));
    
                spaces.add(new ParkingSpace("MAD1-44", 155, 330, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-45", 190, 330, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-46", 225, 330, Direction.Down, PlugType.CHAdeMO, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("MAD1-47", 255, 330, Direction.Down, PlugType.CHAdeMO, ParkingState.Available, madrid1));
                spaces.add(new ParkingSpace("MAD1-48", 290, 330, Direction.Down, PlugType.CHAdeMO, ParkingState.Blocked, madrid1));
                spaces.add(new ParkingSpace("MAD1-49", 325, 330, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                spaces.add(new ParkingSpace("MAD1-50", 355, 330, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid1));
                
                System.out.println("Madrid-01 places seeder used");
            }else{
                System.out.println("Madrid-01 not found, spaces seeder for this plant is canceled");
            }    


            if(madrid2 !=null){
                //Madrid-2
                spaces.add(new ParkingSpace("MAD2-1", 180, 160, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-2", 210, 160, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-3", 245, 160, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-4", 275, 160, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-5", 310, 160, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-6", 340, 160, Direction.Down, PlugType.Schuko, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("MAD2-7", 375, 160, Direction.Down, PlugType.Schuko, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("MAD2-8", 405, 160, Direction.Down, PlugType.Schuko, ParkingState.Blocked, madrid2));
                spaces.add(new ParkingSpace("MAD2-9", 445, 160, Direction.Down, PlugType.CCS, ParkingState.Blocked, madrid2));
                spaces.add(new ParkingSpace("MAD2-10", 475, 160, Direction.Down, PlugType.CCS, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("MAD2-11", 510, 160, Direction.Down, PlugType.CHAdeMO, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("MAD2-12", 540, 160, Direction.Down, PlugType.CHAdeMO, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("MAD2-13", 575, 160, Direction.Down, PlugType.Type1, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("MAD2-14", 605, 160, Direction.Down, PlugType.Type1, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("MAD2-15", 640, 160, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-16", 670, 160, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-17", 130, 230, Direction.Right, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-18", 130, 260, Direction.Right, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-19", 130, 295, Direction.Right, PlugType.Type2, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("MAD2-20", 130, 325, Direction.Right, PlugType.Type2, ParkingState.Blocked, madrid2));
                spaces.add(new ParkingSpace("MAD2-21", 130, 360, Direction.Right, PlugType.Schuko, ParkingState.Available, madrid2));
                spaces.add(new ParkingSpace("MAD2-22", 130, 390, Direction.Right, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-23", 275, 310, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-24", 310, 310, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-25", 375, 310, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-26", 405, 310, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-27", 507, 310, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-28", 535, 310, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-29", 575, 310, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-30", 605, 310, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-31", 180, 460, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-32", 210, 460, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-33", 245, 460, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-34", 275, 460, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-35", 310, 460, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-36", 340, 460, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-37", 375, 460, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-38", 405, 460, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-39", 445, 460, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-40", 475, 460, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-41", 510, 460, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));
                spaces.add(new ParkingSpace("MAD2-42", 540, 460, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid2));

                System.out.println("Madrid-02 places seeder used");
            }else{
                System.out.println("Madrid-02 not found, spaces seeder for this plant is canceled");
            }


            if(madrid3 !=null){
                spaces.add(new ParkingSpace("MAD3-1", 135, 130, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-2", 170, 130, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-3", 203, 130, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                
                spaces.add(new ParkingSpace("MAD3-4", 240, 130, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-5", 275, 130, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-6", 304, 130, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
              
                spaces.add(new ParkingSpace("MAD3-7", 340, 130, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-8", 375, 130, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
               
                // Centro Izquierda Arriba, 
                spaces.add(new ParkingSpace("MAD3-9", 135, 275, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-10", 170, 275, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-11", 215, 275, Direction.Up, PlugType.CCS, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("MAD3-12", 250, 275, Direction.Up, PlugType.CCS, ParkingState.Blocked, madrid3));
                spaces.add(new ParkingSpace("MAD3-13", 315, 275, Direction.Up, PlugType.CHAdeMO, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("MAD3-14", 345, 275, Direction.Up, PlugType.Type1, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("MAD3-15", 385, 275, Direction.Up, PlugType.Type2, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("MAD3-16", 420, 275, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
            
                // Centro Izquierda Abajo", 
                spaces.add(new ParkingSpace("MAD3-17", 135, 345, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-18", 170, 345, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-19", 385, 345, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-20", 420, 345, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                
                // Centro Derecha, 
                spaces.add(new ParkingSpace("MAD3-21", 550, 265, Direction.Left, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-22", 550, 295, Direction.Left, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-23", 540, 345, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-24", 575, 345, Direction.Down, PlugType.NoType, ParkingState.Unusable, madrid3));
             
                // Abajo", 
                spaces.add(new ParkingSpace("MAD3-25", 135, 500, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-26", 170, 500, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-27", 215, 500, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-28", 250, 500, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-29", 310, 500, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-30", 345, 500, Direction.Up, PlugType.Type1, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("MAD3-31", 385, 500, Direction.Up, PlugType.Type1, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("MAD3-32", 420, 500, Direction.Up, PlugType.Type2, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("MAD3-33", 455, 500, Direction.Up, PlugType.Type2, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("MAD3-34", 495, 500, Direction.Up, PlugType.Schuko, ParkingState.Available, madrid3));
                spaces.add(new ParkingSpace("MAD3-35", 540, 500, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-36", 575, 500, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-37", 615, 500, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
                spaces.add(new ParkingSpace("MAD3-38", 655, 500, Direction.Up, PlugType.NoType, ParkingState.Unusable, madrid3));
               
                System.out.println("Madrid-03 places seeder used");
            }else{
                System.out.println("Madrid-03 not found, spaces seeder for this plant is canceled");
            }

            if(malaga0 !=null){

                spaces.add(new ParkingSpace("MAL0-1", 160, 200, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("MAL0-2", 201, 200, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("MAL0-3", 239, 200, Direction.Down, PlugType.CCS, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("MAL0-4", 277, 200, Direction.Down, PlugType.CCS, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("MAL0-5", 315, 200, Direction.Down, PlugType.CCS, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("MAL0-6", 355, 200, Direction.Down, PlugType.CCS, ParkingState.Occupied, malaga0));
                spaces.add(new ParkingSpace("MAL0-7", 394, 200, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("MAL0-8", 433, 200, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));

                spaces.add(new ParkingSpace("MAL0-9", 586, 200, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("MAL0-10", 625, 200, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("MAL0-11", 665, 200, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga0));

                /* Abajo */

                spaces.add(new ParkingSpace("MAL0-12", 160, 370, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("MAL0-13", 201, 370, Direction.Up, PlugType.CHAdeMO, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("MAL0-14", 239, 370, Direction.Up, PlugType.CHAdeMO, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("MAL0-15", 277, 370, Direction.Up, PlugType.CHAdeMO, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("MAL0-16", 315, 370, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("MAL0-17", 355, 370, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga0));

                spaces.add(new ParkingSpace("MAL0-18", 467, 370, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("MAL0-19", 506, 370, Direction.Up, PlugType.Type1, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("MAL0-20", 546, 370, Direction.Up, PlugType.Type2, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("MAL0-21", 586, 370, Direction.Up, PlugType.Schuko, ParkingState.Available, malaga0));
                spaces.add(new ParkingSpace("MAL0-22", 625, 370, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga0));
                spaces.add(new ParkingSpace("MAL0-23", 665, 370, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga0));



                System.out.println("Málaga-00 places seeder used");
            }else{
                System.out.println("Málaga-00 not found, spaces seeder for this plant is canceled");
            }


            if(malaga1 !=null){

                /* Arriba */
                spaces.add(new ParkingSpace("MAL1-1", 75, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-2", 110, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-3", 147, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-4", 185, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-5", 222, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-6", 256, 140, Direction.Down, PlugType.Schuko, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("MAL1-7", 293, 140, Direction.Down, PlugType.Schuko, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("MAL1-8", 330, 140, Direction.Down, PlugType.Schuko, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("MAL1-9", 365, 140, Direction.Down, PlugType.Schuko, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("MAL1-10", 400, 140, Direction.Down, PlugType.Type1, ParkingState.Blocked, malaga1));
                spaces.add(new ParkingSpace("MAL1-11", 437, 140, Direction.Down, PlugType.Type1, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("MAL1-12", 474, 140, Direction.Down, PlugType.Type2, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("MAL1-13", 510, 140, Direction.Down, PlugType.CCS, ParkingState.Blocked, malaga1));
                spaces.add(new ParkingSpace("MAL1-14", 545, 140, Direction.Down, PlugType.CCS, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("MAL1-15", 583, 140, Direction.Down, PlugType.CHAdeMO, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("MAL1-16", 617, 140, Direction.Down, PlugType.CHAdeMO, ParkingState.Available, malaga1));
                spaces.add(new ParkingSpace("MAL1-17", 655, 140, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));

                /* Centro Arriba */
                spaces.add(new ParkingSpace("MAL1-18", 75, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-19", 110, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-20", 147, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-21", 185, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-22", 222, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-23", 256, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-24", 293, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-25", 330, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-26", 365, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-27", 400, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-28", 437, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-29", 474, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-30", 510, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-31", 545, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-32", 583, 280, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));

                /* Centro Abajo */
                spaces.add(new ParkingSpace("MAL1-33", 75, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-34", 110, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-35", 147, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-36", 185, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-37", 222, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-38", 256, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-39", 293, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-40", 330, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-41", 365, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-42", 400, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-43", 437, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-44", 474, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-45", 510, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-46", 545, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-47", 583, 350, Direction.Down, PlugType.NoType, ParkingState.Unusable, malaga1));

                /* Abajo */
                spaces.add(new ParkingSpace("MAL1-48", 110, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-49", 147, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-50", 185, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-51", 222, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-52", 260, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-53", 297, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-54", 333, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-55", 370, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-56", 407, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-57", 444, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-58", 480, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-59", 517, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));
                spaces.add(new ParkingSpace("MAL1-60", 552, 490, Direction.Up, PlugType.NoType, ParkingState.Unusable, malaga1));



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
