package com.dsoccer1980.fuel.service;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionDto;

import java.util.List;

public interface FuelConsumptionService {

    FuelConsumption create(FuelConsumptionDto fuelConsumptionDto);

    FuelConsumption update(FuelConsumptionDto fuelConsumptionDto);

    void delete(long id);

    List<FuelConsumption> findAll();

    List<FuelConsumption> findByDriverId(long id);

}
