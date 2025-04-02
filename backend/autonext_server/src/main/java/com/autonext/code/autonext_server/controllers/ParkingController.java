package com.autonext.code.autonext_server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.dto.BookingDTO;
import com.autonext.code.autonext_server.dto.ParkingCenterDTO;
import com.autonext.code.autonext_server.dto.ParkingLevelMapDTO;
import com.autonext.code.autonext_server.dto.ParkingSpaceDTO;
import com.autonext.code.autonext_server.mapper.BookingMapper;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.services.CentersService;
import com.autonext.code.autonext_server.services.ParkingService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
  @SecurityRequirement(name = "bearerAuth")
  public ResponseEntity<?> getLevelWithAvailability(
      @PathVariable int id,
      @RequestParam(required = false) LocalDate date,
      @RequestParam(required = false) Integer plugType,
      @RequestParam(required = false) String startTime,
      @RequestParam(required = false) String endTime) {

    try {
      User user = getAuthenticatedUser();
      ParkingLevelMapDTO dto = parkingService.getFilteredLevelMap(id, date, plugType, startTime, endTime, user);
      return ResponseEntity.ok(dto);
    } catch (RuntimeException e) {
      return ResponseEntity.status(404).body("No se encontró el mapa con id " + id + ": " + e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error inesperado al obtener el mapa: " + e.getMessage());
    }
  }

  @GetMapping("/centers")
  public ResponseEntity<?> getCenters() {
    try {
      List<ParkingCenterDTO> dto = centersService.getParkingCenters();
      return ResponseEntity.ok(dto);

    } catch (RuntimeException e) {
      return ResponseEntity
          .status(404)
          .body("No se encontraron mapas" + ": " + e.getMessage());

    } catch (Exception e) {
      return ResponseEntity
          .status(500)
          .body("Error inesperado al obtener los mapas: " + e.getMessage());
    }

  }

  private User getAuthenticatedUser() {
    UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
        .getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
    return user;
  }

}
