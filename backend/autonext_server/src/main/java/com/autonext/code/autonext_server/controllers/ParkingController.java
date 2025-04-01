package com.autonext.code.autonext_server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.dto.ParkingCenterDTO;
import com.autonext.code.autonext_server.dto.ParkingLevelMapDTO;

import com.autonext.code.autonext_server.services.CentersService;
import com.autonext.code.autonext_server.services.ParkingService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final CentersService centersService;

    public ParkingController(ParkingService parkingService, CentersService centersService) {
        this.parkingService = parkingService;
        this.centersService = centersService;
    }

    @GetMapping("/level/{id}")
    public ResponseEntity<?> getMapByLevel(@PathVariable int id) {
        try {
            ParkingLevelMapDTO dto = parkingService.getLevelMap(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(404)
                    .body("No se encontró el mapa con id " + id + ": " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Error inesperado al obtener el mapa: " + e.getMessage());
        }
    }

    @GetMapping("/centers")
    public ResponseEntity<?> getCenters() {
        try{
           List<ParkingCenterDTO> dto = centersService.getParkingCenters();
           return ResponseEntity.ok(dto);


        }catch(RuntimeException e){
            return ResponseEntity
                .status(404)
                .body("No se encontraron mapas" + ": " + e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Error inesperado al obtener los mapas: " + e.getMessage());
        }

        
    }
    
}
