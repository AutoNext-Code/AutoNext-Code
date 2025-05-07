package com.autonext.code.autonext_server.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.dto.BookingDTO;
import com.autonext.code.autonext_server.dto.ParkingLevelMapDTO;
import com.autonext.code.autonext_server.mapper.BookingMapper;
import com.autonext.code.autonext_server.models.Booking;
import com.autonext.code.autonext_server.services.ParkingService;
import com.autonext.code.autonext_server.services.SpaceService;


import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/admin/space")
public class AdminSpaceController {
    
    @Autowired
    private SpaceService spaceService;

    @Autowired ParkingService parkingService;

    @PutMapping("/state")
    public ResponseEntity<String> spaceState(@RequestParam int id, @RequestParam boolean blocked) {
        try{

            spaceService.updateActiveStatus(id, blocked);


            return ResponseEntity.ok("Plaza actualizada correctamente");

        }catch(Error e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }


    @GetMapping("/bookings/{id}")
    public Page<BookingDTO>  getBookingsBySpace(@PathVariable int id, @RequestParam(defaultValue = "0") int page) {

        PageRequest pageable = buildPageRequest(page, true);

        Page<Booking> bookings = spaceService.getBookingPage(pageable, id);

        List<BookingDTO> bookingDTOs = bookings.getContent().stream()
        .map(BookingMapper::toDTO)
        .toList();


        return new PageImpl<>(bookingDTOs, pageable, bookings.getTotalElements());
            


    }


    private PageRequest buildPageRequest(int page, boolean ascending) {
        Sort sort = ascending ? Sort.by("date").ascending() : Sort.by("date").descending();
        return PageRequest.of(page, 6, sort);
    }


    @GetMapping("/level/{id}")
    public ResponseEntity<?> getLevelWithAvailability(
        @PathVariable int id) {

        try {
        ParkingLevelMapDTO dto = parkingService.adminLevelMap(id);
        return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
        return ResponseEntity.status(404).body("No se encontró el mapa con id " + id + ": " + e.getMessage());
        } catch (Exception e) {
        return ResponseEntity.status(500).body("Error inesperado al obtener el mapa: " + e.getMessage());
        }
    }
    
    
}
