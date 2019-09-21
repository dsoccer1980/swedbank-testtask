package com.dsoccer1980.fuel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fuel_seq_gen")
    @SequenceGenerator(name = "fuel_seq_gen", sequenceName = "fuel_id_seq")
    private long id;

    private String fuelType;

    private double price;

    private double volume;

    private LocalDate date;

    private long driverId;

    public FuelConsumption(String fuelType, double price, double volume, LocalDate date, long driverId) {
        this.fuelType = fuelType;
        this.price = price;
        this.volume = volume;
        this.date = date;
        this.driverId = driverId;
    }
}
