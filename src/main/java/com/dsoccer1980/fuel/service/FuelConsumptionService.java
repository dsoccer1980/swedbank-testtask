package com.dsoccer1980.fuel.service;

import com.dsoccer1980.fuel.domain.FuelConsumption;

import java.util.List;

public interface FuelConsumptionService {

    FuelConsumption create(FuelConsumption fuelConsumption);

    FuelConsumption update(FuelConsumption fuelConsumption);

    void delete(long id);

    List<FuelConsumption> findAll();

    List<FuelConsumption> findByDriverId(long id);

}
