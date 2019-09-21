package com.dsoccer1980.fuel.repository;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelConsumptionRepository extends JpaRepository<FuelConsumption, Long> {

    List<FuelConsumption> findByDriverId(long id);
}
