package com.dsoccer1980.fuel.service;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.FuelType;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionDto;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionStatistic;
import com.dsoccer1980.fuel.domain.dto.MoneyByMonth;
import com.dsoccer1980.fuel.repository.FuelConsumptionRepository;
import com.dsoccer1980.fuel.repository.FuelTypeRepository;
import com.dsoccer1980.fuel.util.Utils;
import com.dsoccer1980.fuel.util.exception.NotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuelConsumptionServiceImpl implements FuelConsumptionService {

    private final FuelConsumptionRepository repository;
    private final FuelTypeRepository fuelTypeRepository;

    @Override
    public FuelConsumption create(FuelConsumptionDto fuelConsumptionDto) {
        FuelType fuelType = fuelTypeRepository
                .findById(fuelConsumptionDto.getFuelTypeId())
                .orElseThrow(() -> new NotFoundException("Not found fuelType with id: " + fuelConsumptionDto.getFuelTypeId()));
        FuelConsumption fuelConsumption = FuelConsumptionDto.getFuelConsumption(fuelConsumptionDto, fuelType);
        return repository.save(fuelConsumption);
    }

    @Override
    public FuelConsumption update(FuelConsumptionDto fuelConsumptionDto) {
        return create(fuelConsumptionDto);
    }

    @Override
    public void saveMultipart(MultipartFile file) {
        try {
            String json = new BufferedReader(new InputStreamReader(file.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));
            List<FuelConsumptionDto> listDtoFromJson = new ObjectMapper()
                    .readValue(json, new TypeReference<List<FuelConsumptionDto>>() {});
            listDtoFromJson.forEach(this::create);
        } catch (IOException e) {
            throw new NotFoundException("Error during reading file");
        }
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

    @Override
    public List<MoneyByMonth> sumSpentMoneyGroupByMonth() {
        return repository.sumSpentMoneyGroupByMonth();
    }

    @Override
    public List<FuelConsumption> findFuelConsumptionByMonth(int month) {
        List<FuelConsumption> consumptions = repository.findFuelConsumptionByMonth(month);
        consumptions.forEach(Utils::setTotalPrice);
        return consumptions;
    }

    @Override
    public List<FuelConsumptionStatistic> findFuelConsumptionGroupByFuelType() {
        return repository.findFuelConsumptionGroupByFuelType();
    }

    @Override
    public List<MoneyByMonth> sumSpentMoneyByDriverIdGroupByMonth(long driverId) {
        return repository.sumSpentMoneyByDriverIdGroupByMonth(driverId);
    }

    @Override
    public List<FuelConsumption> findFuelConsumptionByMonthAndByDriverId(int month, long driverId) {
        List<FuelConsumption> consumptions = repository.findFuelConsumptionByMonthAndByDriverId(month, driverId);
        consumptions.forEach(Utils::setTotalPrice);
        return consumptions;
    }

    @Override
    public List<FuelConsumptionStatistic> findFuelConsumptionByDriverIdGroupByFuelType(long driverId) {
        return repository.findFuelConsumptionByDriverIdGroupByFuelType(driverId);
    }
}
