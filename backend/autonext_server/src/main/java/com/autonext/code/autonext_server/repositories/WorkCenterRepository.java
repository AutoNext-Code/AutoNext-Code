package com.autonext.code.autonext_server.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.autonext.code.autonext_server.models.WorkCenter;

@Repository
public interface WorkCenterRepository extends CrudRepository<WorkCenter, Integer> {

    Optional<WorkCenter> findByName(String name);

    
}
