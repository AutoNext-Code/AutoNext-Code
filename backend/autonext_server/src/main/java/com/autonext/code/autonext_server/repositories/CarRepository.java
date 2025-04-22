package com.autonext.code.autonext_server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.autonext.code.autonext_server.models.Car;
import com.autonext.code.autonext_server.models.User;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
  
  Optional<Car> findByCarPlate(String carPlate);

  

  List<Car> findByUser(User user);

  @Modifying
  @Query("Delete from Car c where c.id=?1")
  int delByIdPer(int id);

  /* @Query("SELECT c FROM Car c WHERE c.user=:user AND c.carName=:carName")
  Optional<Car> findCarNameByUser(@Param("name") String carName, @Param("user") User user); */

  Optional<Car> findByNameAndUser(String name, User user);



    
}
