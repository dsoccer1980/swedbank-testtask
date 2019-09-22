package com.dsoccer1980.fuel.domain.dto;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.FuelType;
import com.dsoccer1980.fuel.util.CustomJsonDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private LocalDate date;

    private long driverId;

    public FuelConsumptionDto(long fuelTypeId, double price, double volume, LocalDate date, long driverId) {
        this.fuelTypeId = fuelTypeId;
        this.price = price;
        this.volume = volume;
        this.date = date;
        this.driverId = driverId;
    }

    public FuelConsumptionDto(FuelConsumption fuelConsumption) {
        this.id = fuelConsumption.getId();
        this.fuelTypeId = fuelConsumption.getFuelType().getId();
        this.price = fuelConsumption.getPrice();
        this.volume = fuelConsumption.getVolume();
        this.date = fuelConsumption.getDate();
        this.driverId = fuelConsumption.getDriverId();
    }

    public static FuelConsumption getFuelConsumption(FuelConsumptionDto dto, FuelType fuelType) {
        return new FuelConsumption(dto.getId(), fuelType, dto.getPrice(), dto.getVolume(), dto.getDate(), dto.getDriverId());
    }
}
