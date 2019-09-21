package com.dsoccer1980.fuel.controller;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionDto;
import com.dsoccer1980.fuel.service.FuelConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FuelConsumptionRestController {

    private final FuelConsumptionService fuelConsumptionService;

    @GetMapping("/consumption")
    public List<FuelConsumption> findAll() {
        return fuelConsumptionService.findAll();
    }

    @PostMapping(value = "/consumption")
    public FuelConsumption save(@RequestBody FuelConsumptionDto dto) {
        return fuelConsumptionService.create(dto);
    }
}
