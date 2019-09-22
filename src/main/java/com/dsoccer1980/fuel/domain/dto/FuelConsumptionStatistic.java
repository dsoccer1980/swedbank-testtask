package com.dsoccer1980.fuel.domain.dto;

import com.dsoccer1980.fuel.domain.FuelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelConsumptionStatistic {

    private int month;

    private FuelType fuelType;

    private double volume;

    private double averagePrice;

    private double total;
}
