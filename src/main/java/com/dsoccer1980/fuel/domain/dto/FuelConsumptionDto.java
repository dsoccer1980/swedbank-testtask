package com.dsoccer1980.fuel.domain.dto;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.FuelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelConsumptionDto {

    private long id;

    private long fuelTypeId;

    private double price;

    private double volume;

    private LocalDate date;

    private long driverId;

    public FuelConsumptionDto(long fuelTypeId, double price, double volume, LocalDate date, long driverId) {
        this.fuelTypeId = fuelTypeId;
        this.price = price;
        this.volume = volume;
        this.date = date;
        this.driverId = driverId;
    }

    public static FuelConsumption getFuelConsumption(FuelConsumptionDto dto, FuelType fuelType) {
        return new FuelConsumption(dto.getId(), fuelType, dto.getPrice(), dto.getVolume(), dto.getDate(), dto.getDriverId());
    }
}
