package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.ParkingLevelMapDTO;
import com.autonext.code.autonext_server.mapper.ParkingSpaceMapper;
import com.autonext.code.autonext_server.models.ParkingLevel;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.ParkingLevelRepository;
import com.autonext.code.autonext_server.repositories.ParkingSpaceRepository;
import com.autonext.code.autonext_server.specifications.ParkingSpaceSpecifications;
import com.autonext.code.autonext_server.repositories.WorkCenterRepository;
import com.autonext.code.autonext_server.models.WorkCenter;

@Service
public class ParkingService {

  @Autowired
  private ParkingSpaceRepository parkingSpaceRepository;

  @Autowired
  private ParkingLevelRepository parkingLevelRepository;

  @Autowired
  private WorkCenterRepository workCenterRepository;

  // @Autowired
  // private ParkingSpaceMapper parkingSpaceMapper;

  @Value("${app.image-base-url}")
  private String imageBaseUrl;

  public ParkingLevelMapDTO getFilteredLevelMap(int levelId,
      LocalDate date,
      String startTime,
      String endTime,
      User user) {

    ParkingLevel level = parkingLevelRepository.findById(levelId)
        .orElseThrow(() -> new RuntimeException("No se ha encontrado el nivel con id: " + levelId));

        //TODO añadir a spec que compruebe que el jobPos del user y la plaza sean el mismo
    Specification<ParkingSpace> spec = Specification
        .where(ParkingSpaceSpecifications.isElectric())
        .and(ParkingSpaceSpecifications.hasLevel(levelId))
        .and(ParkingSpaceSpecifications.jobPositionAllowed(user.getJobPosition()));

    List<ParkingSpace> spaces = parkingSpaceRepository.findAll(spec);

    List<ParkingLevelMapDTO.Space> spaceDTOs = spaces.stream()
        .map(space -> ParkingSpaceMapper.toSpaceDTO(space, date, startTime, endTime, user))
        .toList();

    return new ParkingLevelMapDTO(level, imageBaseUrl, spaceDTOs);
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