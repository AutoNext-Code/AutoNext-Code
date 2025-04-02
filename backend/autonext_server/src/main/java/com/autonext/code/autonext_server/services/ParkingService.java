package com.autonext.code.autonext_server.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.ParkingLevelMapDTO;
import com.autonext.code.autonext_server.dto.ParkingSpaceDTO;
import com.autonext.code.autonext_server.mapper.ParkingSpaceMapper;
import com.autonext.code.autonext_server.models.ParkingLevel;
import com.autonext.code.autonext_server.models.ParkingSpace;
import com.autonext.code.autonext_server.models.User;
import com.autonext.code.autonext_server.repositories.ParkingLevelRepository;
import com.autonext.code.autonext_server.repositories.ParkingSpaceRepository;
import com.autonext.code.autonext_server.specifications.ParkingSpaceSpecifications;

@Service
public class ParkingService {

  private final ParkingSpaceRepository parkingSpaceRepository;
  private final ParkingLevelRepository parkingLevelRepository;

  @Value("${app.image-base-url}")
  private String imageBaseUrl;

  public ParkingService(ParkingSpaceRepository parkingSpaceRepository,
      ParkingSpaceMapper parkingSpaceMapper,
      ParkingLevelRepository parkingLevelRepository) {
    this.parkingSpaceRepository = parkingSpaceRepository;
    this.parkingLevelRepository = parkingLevelRepository;
  }

  public ParkingLevelMapDTO getFilteredLevelMap(int levelId,
      LocalDate date,
      Integer plugType,
      String startTime,
      String endTime,
      User user) {

    ParkingLevel level = parkingLevelRepository.findById(levelId)
        .orElseThrow(() -> new RuntimeException("No se ha encontrado el nivel con id: " + levelId));

    Specification<ParkingSpace> spec = Specification
        .where(ParkingSpaceSpecifications.isElectric())
        .and(ParkingSpaceSpecifications.hasLevel(levelId));

    if (plugType != null) {
      spec = spec.and(ParkingSpaceSpecifications.hasPlugType(plugType));
    }

    List<ParkingSpace> spaces = parkingSpaceRepository.findAll(spec);

    List<ParkingLevelMapDTO.Space> spaceDTOs = spaces.stream()
        .map(space -> ParkingSpaceMapper.toSpaceDTO(space, date, startTime, endTime, user))
        .toList();

    return new ParkingLevelMapDTO(level, imageBaseUrl, spaceDTOs);
  }

}
