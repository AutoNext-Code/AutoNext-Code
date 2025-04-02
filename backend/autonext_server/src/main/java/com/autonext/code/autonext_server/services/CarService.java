package com.autonext.code.autonext_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.CarDTO;
import com.autonext.code.autonext_server.exceptions.UserNotFoundException;
import com.autonext.code.autonext_server.mapper.CarMapper;
import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.CarRepository;
import com.autonext.code.autonext_server.repositories.UserRepository;

@Service
public class CarService {
    
    @Autowired
    private CarRepository carRepository;

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

}
