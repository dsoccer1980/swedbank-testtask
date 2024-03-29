package com.dsoccer1980.fuel.controller;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionDto;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionStatistic;
import com.dsoccer1980.fuel.domain.dto.MoneyByMonth;
import com.dsoccer1980.fuel.service.FuelConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FuelConsumptionRestController {

    private final FuelConsumptionService service;

    @PostMapping("/consumption")
    public FuelConsumption save(@RequestBody FuelConsumptionDto dto) {
        return service.create(dto);
    }

    @PostMapping("/consumption/bulk")
    public ResponseEntity<?> saveBulk(@RequestParam("file") MultipartFile file) {
        service.saveMultipart(file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/consumption/money")
    public List<MoneyByMonth> sumSpentMoneyGroupByMonth() {
        return service.sumSpentMoneyGroupByMonth();
    }

    @GetMapping("/consumption/money/driver/{id}")
    public List<MoneyByMonth> sumSpentMoneyByDriverIdGroupByMonth(@PathVariable("id") long driverId) {
        return service.sumSpentMoneyByDriverIdGroupByMonth(driverId);
    }

    @GetMapping("/consumption/month/{month}")
    public List<FuelConsumption> findFuelConsumptionByMonth(@PathVariable("month") int month) {
        return service.findFuelConsumptionByMonth(month);
    }

    @GetMapping("/consumption/month/{month}/driver/{id}")
    public List<FuelConsumption> findFuelConsumptionByMonthAndByDriverId(@PathVariable("month") int month, @PathVariable("id") long driverId) {
        return service.findFuelConsumptionByMonthAndByDriverId(month, driverId);
    }

    @GetMapping("/consumption/statistic")
    public List<FuelConsumptionStatistic> findFuelConsumptionGroupByFuelType() {
        return service.findFuelConsumptionGroupByFuelType();
    }

    @GetMapping("/consumption/statistic/driver/{id}")
    public List<FuelConsumptionStatistic> findFuelConsumptionByDriverIdGroupByFuelType(@PathVariable("id") long driverId) {
        return service.findFuelConsumptionByDriverIdGroupByFuelType(driverId);
    }
}