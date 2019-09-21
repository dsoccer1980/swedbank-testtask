package com.dsoccer1980.fuel.service;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.repository.FuelConsumptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelConsumptionServiceImpl implements FuelConsumptionService {

    private final FuelConsumptionRepository repository;

    public FuelConsumptionServiceImpl(FuelConsumptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public FuelConsumption create(FuelConsumption fuelConsumption) {
        return repository.save(fuelConsumption);
    }

    @Override
    public FuelConsumption update(FuelConsumption fuelConsumption) {
        return repository.save(fuelConsumption);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<FuelConsumption> findAll() {
        return repository.findAll();
    }

    @Override
    public List<FuelConsumption> findByDriverId(long id) {
        return repository.findByDriverId(id);
    }
}
