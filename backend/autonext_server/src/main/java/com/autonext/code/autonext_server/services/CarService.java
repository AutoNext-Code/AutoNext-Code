package com.autonext.code.autonext_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.CarDTO;
import com.autonext.code.autonext_server.exceptions.ActiveBookingsException;
import com.autonext.code.autonext_server.exceptions.CarAlreadyExistsException;
import com.autonext.code.autonext_server.exceptions.CarNotExistsException;
import com.autonext.code.autonext_server.exceptions.CarOwnerException;
import com.autonext.code.autonext_server.exceptions.CarsOwnedException;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.mapper.CarMapper;
import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.models.enums.BookingStatus;
import com.autonext.code.autonext_server.repositories.BookingRepository;
import com.autonext.code.autonext_server.repositories.CarRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;

@Service
public class CarService {
    
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CarDTO> GetCarsByUserId(int userId) {
        
        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty()) {
                throw new UserNotFoundException("Usuario no existente.");
            }
    
            List<Car> cars = carRepository.findByUser(user.get());
            
            List<CarDTO> carDTOs = CarMapper.toListCarDTO(cars);
    
            return carDTOs;
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener coches del usuario.", e);
        }

    }

    public void createCar(CarDTO carDTO){
        int userId = getAuthenticatedUserId();

        if (carRepository.findByCarPlate(carDTO.getCarPlate()).isPresent()){
            throw new CarAlreadyExistsException("La matrícula ya está registrada en el sistema");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));


        Car car = new Car(
            carDTO.getCarPlate(),
            carDTO.getName(),
            carDTO.getPlugType(),
            user
        );

        carRepository.save(car);


    }

    public void deleteCar(int id){
        int userId = getAuthenticatedUserId();

        Car car = carRepository.findById(id)
            .orElseThrow(() -> new CarNotExistsException("Vehículo no encontrado"));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuario no encotrado"));


        if(car.getUser()==user){

            boolean pendingBooking = car.getBookings().stream()
                .anyMatch(booking -> 
                    booking.getStatus().equals(BookingStatus.Active) || 
                    booking.getStatus().equals(BookingStatus.Pending)
                );
            
            if(!pendingBooking){

                if(user.getCars().size()>1){
                    bookingRepository.carDeletionUnbound(car.getId());
    
                    carRepository.delByIdPer(car.getId());
                }else{
                    throw new CarsOwnedException("Es el único vehículo registrado");
                }

            }else{
                throw new ActiveBookingsException("El vehículo tiene reservas pendientes");
            }
            
            
        }else{
            throw new CarOwnerException("El vehículo no le pertenece al usuario registrado");
        }

    }


    private int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
        return user.getId();
        }

        throw new SecurityException("Usuario no autenticado correctamente");
    }


    

}
