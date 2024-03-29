package com.dsoccer1980.fuel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "fuel_consumption")
public class FuelConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @NotNull
    private FuelType fuelType;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "volume", nullable = false)
    private double volume;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "driver_id", nullable = false)
    private long driverId;

    @Transient
    private double totalPrice;

    public FuelConsumption(long id, FuelType fuelType, double price, double volume, LocalDate date, long driverId) {
        this.id = id;
        this.fuelType = fuelType;
        this.price = price;
        this.volume = volume;
        this.date = date;
        this.driverId = driverId;
    }

    public FuelConsumption(FuelType fuelType, double price, double volume, LocalDate date, long driverId) {
        this.fuelType = fuelType;
        this.price = price;
        this.volume = volume;
        this.date = date;
        this.driverId = driverId;
    }

    public FuelConsumption(FuelConsumption f) {
        this(f.getId(), f.getFuelType(), f.getPrice(), f.getVolume(), f.getDate(), f.getDriverId());
    }
}
