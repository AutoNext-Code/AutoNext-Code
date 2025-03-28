package com.autonext.code.autonext_server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.services.ParkingService;



@RestController
@RequestMapping("/api/parking")
public class ParkingController {
    
    private final ParkingService parkingService ;

    public ParkingController( ParkingService parkingService ) {
        this.parkingService = parkingService;
    }
}
