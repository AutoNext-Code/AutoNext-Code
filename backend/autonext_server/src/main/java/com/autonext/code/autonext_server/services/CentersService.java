package com.autonext.code.autonext_server.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.dto.ParkingCenterDTO;
import com.autonext.code.autonext_server.models.WorkCenter;
import com.autonext.code.autonext_server.repositories.WorkCenterRepository;

@Service
public class CentersService {
    private final WorkCenterRepository workCenterRepository;

    public CentersService(WorkCenterRepository workCenterRepository){
        this.workCenterRepository = workCenterRepository;
    }

    public List<ParkingCenterDTO> getParkingCenters(){
        List<WorkCenter> centros = StreamSupport.stream(workCenterRepository.findAll().spliterator(), false).collect(Collectors.toList());

        return centros
            .stream()
            .map(ParkingCenterDTO::new)
            .collect(Collectors.toList());
    }
}
