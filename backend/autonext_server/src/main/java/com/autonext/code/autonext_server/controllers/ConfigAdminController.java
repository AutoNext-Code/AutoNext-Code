package com.autonext.code.autonext_server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.exceptions.InvalidParkingLimitException;
import com.autonext.code.autonext_server.services.ConfigAdminService;

@RestController
@RequestMapping("/api/admin/config")
public class ConfigAdminController {

    @Autowired
    private ConfigAdminService configAdminService;

    @PutMapping("/update-parking-limit")
    public ResponseEntity<String> updateParkingLimit(@RequestParam Integer parkingLimit) {
        try {
            configAdminService.updateParkingLimit(parkingLimit);
            return ResponseEntity.ok("El límite de estacionamiento fue actualizado");
        } catch (InvalidParkingLimitException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/parking-limit")
    public ResponseEntity<Integer> getParkingLimit() {
        try {
            int limit = configAdminService.getParkingLimit();
            return ResponseEntity.ok(limit);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
