package com.autonext.code.autonext_server.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.autonext.code.autonext_server.models.ParkingSlot;

@Repository
public interface ParkingSlotRepository extends CrudRepository<ParkingSlot, Integer>{
    
}
