package com.autonext.code.autonext_server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.services.SpaceService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/admin/space")
public class AdminSpaceController {
    
    @Autowired
    private SpaceService spaceService;

    @PutMapping("/state")
    public ResponseEntity<String> spaceState(@RequestParam int id, @RequestParam boolean blocked) {
        try{

            spaceService.updateActiveStatus(id, blocked);


            return ResponseEntity.ok("Plaza actualizada correctamente");

        }catch(Error e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }
    
}
