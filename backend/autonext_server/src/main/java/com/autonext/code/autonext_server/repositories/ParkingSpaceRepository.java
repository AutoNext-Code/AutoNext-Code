package com.autonext.code.autonext_server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.autonext.code.autonext_server.models.ParkingLevel;
import com.autonext.code.autonext_server.models.ParkingSpace;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Integer>, JpaSpecificationExecutor<ParkingSpace> {

    List<ParkingSpace> findByParkingLevel(ParkingLevel parkingLevel);

}
