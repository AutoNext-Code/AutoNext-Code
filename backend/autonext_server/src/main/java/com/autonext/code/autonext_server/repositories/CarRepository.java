package com.autonext.code.autonext_server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.User;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
  
  Optional<Car> findByCarPlate(String carPlate);

  List<Car> findByUser(User user);

    
}
