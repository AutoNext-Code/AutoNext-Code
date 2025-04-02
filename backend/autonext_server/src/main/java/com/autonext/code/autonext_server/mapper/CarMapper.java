package com.autonext.code.autonext_server.mapper;

import java.util.ArrayList;
import java.util.List;

import com.autonext.code.autonext_server.dto.CarDTO;
import com.autonext.code.autonext_server.models.Car;

public class CarMapper {

    public static CarDTO toCarDTO(Car car) {
        return new CarDTO(car.getId(), car.getCarPlate(), car.getName(), car.getPlugType());
    }

    public static List<CarDTO> toListCarDTO(List<Car> cars) {
        List<CarDTO> carDTOs = new ArrayList<CarDTO>();

        for (Car car : cars) {
            carDTOs.add(toCarDTO(car));
        }

        return carDTOs;
    }
}
