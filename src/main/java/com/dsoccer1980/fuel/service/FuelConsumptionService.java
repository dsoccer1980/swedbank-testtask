package com.dsoccer1980.fuel.service;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionStatistic;
import com.dsoccer1980.fuel.domain.dto.MoneyByMonth;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionDto;

import java.util.List;

public interface FuelConsumptionService {

    FuelConsumption create(FuelConsumptionDto fuelConsumptionDto);

    FuelConsumption update(FuelConsumptionDto fuelConsumptionDto);

    void delete(long id);

    List<FuelConsumption> findAll();

    List<FuelConsumption> findByDriverId(long id);

    List<MoneyByMonth> sumSpentMoneyGroupByMonth();

    List<FuelConsumption> findFuelConsumptionByMonth(int month);

    List<FuelConsumptionStatistic> findFuelConsumptionGroupByFuelType();

    List<MoneyByMonth> sumSpentMoneyByDriverIdGroupByMonth(long driverId);

    List<FuelConsumption> findFuelConsumptionByMonthAndByDriverId(int month, long driverId);

    List<FuelConsumptionStatistic> findFuelConsumptionByDriverIdGroupByFuelType(long driverId);

}
