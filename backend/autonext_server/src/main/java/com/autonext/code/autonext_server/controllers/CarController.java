package com.autonext.code.autonext_server.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.dto.CarDTO;
import com.autonext.code.autonext_server.exceptions.CarAlreadyExistsException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.services.CarService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public ResponseEntity<?> GetCarsByUser() {
        try {
            UsernamePasswordAuthenticationToken authentication = 
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || authentication.getPrincipal() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado.");
            }

            User user = (User) authentication.getPrincipal();
            List<CarDTO> cars = carService.GetCarsByUserId(user.getId());

            return ResponseEntity.ok(cars);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PutMapping("")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<String> createCar(@Valid @RequestBody CarDTO carDTO) {
        //Optional id in CarDTO, if !=null inconsequential

        try{

            carService.createCar(carDTO);
            return ResponseEntity.ok("Vehículo registrado correctamente");

        }catch(CarAlreadyExistsException cae){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La matrícula del coche ya está registrada");
        }catch(UserNotFoundException unf){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no existe");
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
        
    }
}
