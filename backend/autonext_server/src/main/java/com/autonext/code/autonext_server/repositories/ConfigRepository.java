package com.autonext.code.autonext_server.repositories;

import com.autonext.code.autonext_server.models.Config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {
    
    Config findTopByOrderByIdAsc();
}