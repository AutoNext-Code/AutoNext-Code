package com.autonext.code.autonext_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.CarDTO;
import com.autonext.code.autonext_server.exceptions.ActiveBookingsException;
import com.autonext.code.autonext_server.exceptions.CarNameInUseException;
import com.autonext.code.autonext_server.exceptions.CarNotExistsException;
import com.autonext.code.autonext_server.exceptions.CarPlateAlreadyExistsException;
import com.autonext.code.autonext_server.exceptions.ParamNotValidException;
import com.autonext.code.autonext_server.exceptions.OwnerException;
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

    public void createCar(CarDTO carDTO) {
        int userId = getAuthenticatedUserId();

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));


        //Control
        if(carPlateControl(carDTO.getCarPlate()) && validFormat(carDTO.getCarPlate()) && carNameControl(carDTO.getName(), user)){

            Car car = new Car(
            carDTO.getCarPlate(),
            carDTO.getName(),
            carDTO.getPlugType(),
            user);

            carRepository.save(car);

        }

    }





    public void deleteCar(int id) {
        int userId = getAuthenticatedUserId();

        Car car = carRepository.findById(id)
            .orElseThrow(() -> new CarNotExistsException("Vehículo no encontrado"));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuario no encotrado"));

        if (car.getUser() != user) {
            throw new OwnerException("El vehículo no le pertenece al usuario registrado");
        }

        //Reformular para que sea desde consulta mysql
        boolean pendingBooking = car.getBookings().stream()
            .anyMatch(booking -> booking.getStatus().equals(BookingStatus.Active) ||
                booking.getStatus().equals(BookingStatus.Pending));

        if (pendingBooking) {
            throw new ActiveBookingsException("El vehículo tiene reservas pendientes");
        }

        if (user.getCars().size() < 2) {
            throw new CarsOwnedException("Es el único vehículo registrado");
        }


        bookingRepository.carDeletionUnbound(car.getId());

        carRepository.delByIdPer(car.getId());
        
    }




    public void updateCar(CarDTO carDTO) {

        int userId = getAuthenticatedUserId();

        Car car = carRepository.findById(carDTO.getId())
            .orElseThrow(() -> new CarNotExistsException("Vehículo no encontrado"));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuario no encotrado"));

        if (!(car.getUser() == user)) {
            throw new OwnerException("El vehículo no le pertenece al usuario registrado");
        }


        if (!car.getCarPlate().equals(carDTO.getCarPlate())) {

            if (validFormat(carDTO.getCarPlate()) && carPlateControl(carDTO.getCarPlate())) {
                car.setCarPlate(carDTO.getCarPlate());
            }

        }

        if (!car.getName().equals(carDTO.getName())) {

            if (validFormat(carDTO.getName()) && carNameControl(carDTO.getName(), user)) {
                car.setName(carDTO.getName());
            }

        }

        car.setPlugType(carDTO.getPlugType());

    }





    private int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return user.getId();
        }

        throw new SecurityException("Usuario no autenticado correctamente");
    }



    private boolean carNameControl(String carName, User user){

        boolean valid = true;

        if (carRepository.findByNameAndUser(carName, user).isPresent()) {
            valid = false;
            throw new CarNameInUseException("El usuario ya tiene un coche con este nombre");
        }


        return valid;
    }



    private boolean carPlateControl(String carPlate){
        boolean valid = true;

        if (carRepository.findByCarPlate(carPlate).isPresent()) {
            valid = false;
            throw new CarPlateAlreadyExistsException("La matrícula ya está en uso");
        }
        return valid;

    }

    private boolean validFormat(String param){
        boolean valid = true;

        if ((param.isBlank()) || param.isEmpty()) {
            valid = false;
            throw new ParamNotValidException("Los parámetros no son válidos");
        }

        return valid;
    }

}



