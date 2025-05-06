package com.autonext.code.autonext_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.exceptions.InvalidParkingLimitException;
import com.autonext.code.autonext_server.models.Config;
import com.autonext.code.autonext_server.repositories.ConfigRepository;


@Service
public class ConfigAdminService {

    @Autowired
    private ConfigRepository configRepository;

    public void updateParkingLimit(int parkingLimit) {

        if (parkingLimit < 1) {
            throw new InvalidParkingLimitException("El límite de estacionamiento debe ser mayor que 0");
        }

        Config config = configRepository.findById(1).orElseThrow(() -> new IllegalStateException("No se ha encontrado la configuración del sistema"));
        if (config == null) {
            throw new IllegalStateException("No se ha encontrado la configuración del sistema");
        }

        config.setDailyLimit(parkingLimit);
        configRepository.save(config);
    }

    public int getParkingLimit() {
        Config config = configRepository.findTopByOrderByIdAsc();
        if (config == null) {
            throw new IllegalStateException("No se ha encontrado la configuración del sistema");
        }
        return config.getDailyLimit();
    }
}
