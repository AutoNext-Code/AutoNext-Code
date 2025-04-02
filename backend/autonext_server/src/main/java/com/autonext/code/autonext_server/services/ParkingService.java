package com.autonext.code.autonext_server.services;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.ParkingLevelMapDTO;
import com.autonext.code.autonext_server.models.ParkingLevel;
import com.autonext.code.autonext_server.repositories.ParkingLevelRepository;
import com.autonext.code.autonext_server.repositories.WorkCenterRepository;
import com.autonext.code.autonext_server.models.WorkCenter;

@Service
public class ParkingService {

  private final ParkingLevelRepository parkingLevelRepository;

  private final WorkCenterRepository workCenterRepository;

  @Value("${app.image-base-url}")
  private String imageBaseUrl;

  public ParkingService(ParkingLevelRepository parkingLevelRepository, WorkCenterRepository workCenterRepository) {
    this.parkingLevelRepository = parkingLevelRepository;
    this.workCenterRepository = workCenterRepository;
  }

  public ParkingLevelMapDTO getLevelMap(int levelId) {
    ParkingLevel level = parkingLevelRepository.findById(levelId)
        .orElseThrow(() -> new RuntimeException("No se ha encontrado el nivel de aparcamiento con id: " + levelId));

    level.getParkingSpaces().size();

    return new ParkingLevelMapDTO(level, imageBaseUrl);
  }

  public Map<Integer, String> getWorkCenters() {
    try {
        Iterable<WorkCenter> workCenters = workCenterRepository.findAll();
        Map<Integer, String> workCentersMap = new HashMap<>();
        
        for (WorkCenter workCenter : workCenters) {
            workCentersMap.put(workCenter.getId(), workCenter.getName());
        }

        return workCentersMap;
    } catch (Exception e) {
        throw new ExecutionException("Surgio un error inesperado");
    }
}

}
