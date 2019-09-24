package com.dsoccer1980.fuel.repository;

import com.dsoccer1980.fuel.domain.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
}
