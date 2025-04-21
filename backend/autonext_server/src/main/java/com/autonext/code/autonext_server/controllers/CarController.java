package com.autonext.code.autonext_server.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autonext.code.autonext_server.dto.CarDTO;
import com.autonext.code.autonext_server.exceptions.ActiveBookingsException;
import com.autonext.code.autonext_server.exceptions.CarAlreadyExistsException;
import com.autonext.code.autonext_server.exceptions.CarNameInUseException;
import com.autonext.code.autonext_server.exceptions.CarNotExistsException;
import com.autonext.code.autonext_server.exceptions.CarPlateAlreadyExistsException;
import com.autonext.code.autonext_server.exceptions.CarPlateNotValidException;
import com.autonext.code.autonext_server.exceptions.OwnerException;
import com.autonext.code.autonext_server.exceptions.CarsOwnedException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.services.CarService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;




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

    @PostMapping("")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<String> createCar(@Valid @RequestBody CarDTO carDTO) {

        try{

            carService.createCar(carDTO);
            return ResponseEntity.ok("Vehículo registrado correctamente");

        }catch(CarAlreadyExistsException cae){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La matrícula del coche ya está registrada");
        }catch(UserNotFoundException unf){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no existe");
        }catch(CarPlateNotValidException cpn){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cpn.getMessage());
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
        
    }

    @DeleteMapping("/{id}")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<String> deleteCar(@PathVariable int id){
        try {

            carService.deleteCar(id);
            return ResponseEntity.ok("Vehículo eliminado correctamente");

        }catch(UserNotFoundException unf){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no existe");
        }catch(CarNotExistsException cne){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El vehículo no existe");
        }catch(CarsOwnedException cso){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Es el único vehículo registrado");
        }catch(OwnerException coe){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El vehículo no le pertenece al usuario registrado");
        }catch(ActiveBookingsException coe){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El vehículo tiene reservas pendientes");
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }

        
    }


    @PutMapping("")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<String> updateCar(@Valid @RequestBody CarDTO carDTO) {
        
        try{

            carService.updateCar(carDTO);
            return ResponseEntity.ok("Vehículo actualizado correctamente");

        }catch(UserNotFoundException unf){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no existe");
        }catch(CarNotExistsException cne){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El vehículo no existe");
        }catch(OwnerException coe){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El vehículo no le pertenece al usuario registrado");
        }catch(CarPlateAlreadyExistsException coe){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("La matrícula ya está en uso");
        }catch(CarNameInUseException coe){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario ya tiene un coche con este nombre");
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }
}
