package com.dsoccer1980.fuel.domain;

public class FuelConsumptionStatistic {

    private int month;

    private FuelType fuelType;

    private double volume;

    private double averagePrice;

    private double total;

    public FuelConsumptionStatistic(int month, FuelType fuelType, double volume, double averagePrice, double total) {
        this.month = month;
        this.fuelType = fuelType;
        this.volume = volume;
        this.averagePrice = averagePrice;
        this.total = total;
    }

    @Override
    public String toString() {
        return "FuelConsumptionStatistic{" +
                "month=" + month +
                ", fuelType=" + fuelType +
                ", volume=" + volume +
                ", averagePrice=" + averagePrice +
                ", total=" + total +
                '}';
    }
}
