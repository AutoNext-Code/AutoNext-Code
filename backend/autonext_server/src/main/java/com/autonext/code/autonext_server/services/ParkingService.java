package com.autonext.code.autonext_server.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.ParkingLevelMapDTO;
import com.autonext.code.autonext_server.models.ParkingLevel;
import com.autonext.code.autonext_server.repositories.ParkingLevelRepository;

@Service
public class ParkingService {

  private final ParkingLevelRepository parkingLevelRepository;

  @Value("${app.image-base-url}")
  private String imageBaseUrl;

  public ParkingService(ParkingLevelRepository parkingLevelRepository) {
    this.parkingLevelRepository = parkingLevelRepository;
  }

  public ParkingLevelMapDTO getLevelMap(int levelId) {
    ParkingLevel level = parkingLevelRepository.findById(levelId)
        .orElseThrow(() -> new RuntimeException("No se ha encontrado el nivel de aparcamiento con id: " + levelId));

    level.getParkingSpaces().size();

    return new ParkingLevelMapDTO(level, imageBaseUrl);
  }
}
