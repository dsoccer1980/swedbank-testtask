package com.dsoccer1980.fuel.repository;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelConsumptionRepository extends JpaRepository<FuelConsumption, Long> {
}
