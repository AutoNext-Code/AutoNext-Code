package com.autonext.code.autonext_server.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.autonext.code.autonext_server.models.Car;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
  
  Optional<Car> findByCarPlate(String carPlate);
    
}
