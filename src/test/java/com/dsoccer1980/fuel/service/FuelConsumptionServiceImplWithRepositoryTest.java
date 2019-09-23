package com.dsoccer1980.fuel.service;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.FuelType;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionDto;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionStatistic;
import com.dsoccer1980.fuel.domain.dto.MoneyByMonth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FuelConsumptionServiceImplWithRepositoryTest {

    private final FuelType FUEL_TYPE1 = new FuelType(1, "95");
    private final FuelType FUEL_TYPE2 = new FuelType(2, "98");
    private final FuelConsumption CONSUMPTION1 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 2.5, LocalDate.of(2019, 8, 22), 1111);
    private final FuelConsumption CONSUMPTION2 = new FuelConsumption(2, FUEL_TYPE2, 11.1, 3.5, LocalDate.of(2019, 9, 22), 1111);
    private final FuelConsumption CONSUMPTION3 = new FuelConsumption(3, FUEL_TYPE2, 12.1, 4.5, LocalDate.of(2019, 8, 21), 2222);
    private final FuelConsumption CONSUMPTION4 = new FuelConsumption(4, FUEL_TYPE1, 13.1, 5.5, LocalDate.of(2019, 9, 20), 2222);
    private final FuelConsumption CONSUMPTION5 = new FuelConsumption(5, FUEL_TYPE1, 14.1, 6.5, LocalDate.of(2019, 9, 19), 2222);
    @Autowired
    private FuelConsumptionService service;

    @Test
    void create() {
        FuelConsumptionDto consumptionDto = new FuelConsumptionDto(FUEL_TYPE1.getId(), 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        FuelConsumption consumption = FuelConsumptionDto.getFuelConsumption(consumptionDto, FUEL_TYPE1);
        FuelConsumption expectedConsumption = new FuelConsumption(consumption);
        expectedConsumption.setId(6);
        FuelConsumption createdConsumption = service.create(consumptionDto);
        assertEquals(expectedConsumption, createdConsumption);
    }

    @Test
    void update() {
        FuelConsumption expectedConsumption = new FuelConsumption(CONSUMPTION1);
        expectedConsumption.setVolume(15.8);
        FuelConsumptionDto consumptionDto = new FuelConsumptionDto(expectedConsumption);
        assertEquals(expectedConsumption, service.update(consumptionDto));
    }

    @Test
    void delete() {
        service.delete(CONSUMPTION1.getId());
        assertEquals(Arrays.asList(CONSUMPTION2, CONSUMPTION3, CONSUMPTION4, CONSUMPTION5), service.findAll());
    }

    @Test
    void findAll() {
        assertEquals(Arrays.asList(CONSUMPTION1, CONSUMPTION2, CONSUMPTION3, CONSUMPTION4, CONSUMPTION5), service.findAll());
    }

    @Test
    void findByDriverId() {
        assertEquals(Arrays.asList(CONSUMPTION1, CONSUMPTION2), service.findByDriverId(1111));
    }

    @Test
    void sumSpentMoneyGroupByMonth() {
        MoneyByMonth moneyByMonth1 = new MoneyByMonth(8, getTotal(CONSUMPTION1, CONSUMPTION3));
        MoneyByMonth moneyByMonth2 = new MoneyByMonth(9,
                CONSUMPTION2.getVolume() * CONSUMPTION2.getPrice() +
                        CONSUMPTION4.getVolume() * CONSUMPTION4.getPrice() +
                        CONSUMPTION5.getVolume() * CONSUMPTION5.getPrice());
        assertEquals(Arrays.asList(moneyByMonth1, moneyByMonth2), service.sumSpentMoneyGroupByMonth());
    }

    @Test
    void sumSpentMoneyByDriverIdGroupByMonth() {
        MoneyByMonth moneyByMonth1 = new MoneyByMonth(8, CONSUMPTION1.getVolume() * CONSUMPTION1.getPrice());
        MoneyByMonth moneyByMonth2 = new MoneyByMonth(9, CONSUMPTION2.getVolume() * CONSUMPTION2.getPrice());
        assertEquals(Arrays.asList(moneyByMonth1, moneyByMonth2), service.sumSpentMoneyByDriverIdGroupByMonth(1111));
    }

    @Test
    void findFuelConsumptionByMonth() {
        assertEquals(Arrays.asList(CONSUMPTION1, CONSUMPTION3), service.findFuelConsumptionByMonth(8));
    }

    @Test
    void findFuelConsumptionByMonthAndByDriverId() {
        assertEquals(Collections.singletonList(CONSUMPTION1), service.findFuelConsumptionByMonthAndByDriverId(8, 1111));
    }

    @Test
    void findFuelConsumptionGroupByFuelType() {
        FuelConsumptionStatistic fuelConsumptionStatistic1 = new FuelConsumptionStatistic(
                8, FUEL_TYPE1, CONSUMPTION1.getVolume(), CONSUMPTION1.getPrice(), getTotal(CONSUMPTION1));
        FuelConsumptionStatistic fuelConsumptionStatistic2 = new FuelConsumptionStatistic(
                8, FUEL_TYPE2, CONSUMPTION3.getVolume(), CONSUMPTION3.getPrice(), getTotal(CONSUMPTION3));
        FuelConsumptionStatistic fuelConsumptionStatistic3 = new FuelConsumptionStatistic(
                9, FUEL_TYPE1, CONSUMPTION4.getVolume() + CONSUMPTION5.getVolume(),
                (CONSUMPTION4.getPrice() + CONSUMPTION5.getPrice()) / 2, getTotal(CONSUMPTION4, CONSUMPTION5));
        FuelConsumptionStatistic fuelConsumptionStatistic4 = new FuelConsumptionStatistic(
                9, FUEL_TYPE2, CONSUMPTION2.getVolume(), CONSUMPTION2.getPrice(), getTotal(CONSUMPTION2));
        assertEquals(Arrays.asList(fuelConsumptionStatistic1, fuelConsumptionStatistic2, fuelConsumptionStatistic3, fuelConsumptionStatistic4),
                service.findFuelConsumptionGroupByFuelType());
    }

    @Test
    void findFuelConsumptionByDriverIdGroupByFuelType() {
        FuelConsumptionStatistic fuelConsumptionStatistic1 = new FuelConsumptionStatistic(
                8, FUEL_TYPE2, CONSUMPTION3.getVolume(), CONSUMPTION3.getPrice(), getTotal(CONSUMPTION3));
        FuelConsumptionStatistic fuelConsumptionStatistic2 = new FuelConsumptionStatistic(
                9, FUEL_TYPE1, CONSUMPTION4.getVolume() + CONSUMPTION5.getVolume(),
                (CONSUMPTION4.getPrice() + CONSUMPTION5.getPrice()) / 2,
                getTotal(CONSUMPTION4, CONSUMPTION5));
        assertEquals(Arrays.asList(fuelConsumptionStatistic1, fuelConsumptionStatistic2),
                service.findFuelConsumptionByDriverIdGroupByFuelType(2222));
    }

    private double getTotal(FuelConsumption... fuelConsumptions) {
        double total = 0;
        for (FuelConsumption fuelConsumption : fuelConsumptions) {
            total += fuelConsumption.getPrice() * fuelConsumption.getVolume();
        }
        return Math.round(total * 100) / 100.0;
    }
}