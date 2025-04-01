package com.autonext.code.autonext_server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.autonext.code.autonext_server.models.ParkingSpace;

@Repository
public interface ParkingSpaceRepository extends CrudRepository<ParkingSpace, Integer> {

    @Query("Select p from ParkingSpace p where p.state!=3")
        List<ParkingSpace> findByState();
}
