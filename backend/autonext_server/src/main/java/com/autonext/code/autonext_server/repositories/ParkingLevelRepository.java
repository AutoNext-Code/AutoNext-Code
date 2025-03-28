package com.autonext.code.autonext_server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.autonext.code.autonext_server.models.ParkingLevel;

@Repository
public interface ParkingLevelRepository extends CrudRepository<ParkingLevel, Integer> {

  Optional<List<ParkingLevel>> findByAllWorkCenter(int workCenterId) ;

}
