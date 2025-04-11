package com.autonext.code.autonext_server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.User;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
  
  Optional<Car> findByCarPlate(String carPlate);

  List<Car> findByUser(User user);



    
}
