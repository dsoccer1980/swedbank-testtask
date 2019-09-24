package com.dsoccer1980.fuel.service;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.FuelType;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionDto;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionStatistic;
import com.dsoccer1980.fuel.domain.dto.MoneyByMonth;
import com.dsoccer1980.fuel.repository.FuelConsumptionRepository;
import com.dsoccer1980.fuel.repository.FuelTypeRepository;
import com.dsoccer1980.fuel.util.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({FuelConsumptionServiceImpl.class})
class FuelConsumptionServiceImplTest {

    private final FuelType FUEL_TYPE1 = new FuelType(1, "95");
    @Autowired
    private FuelConsumptionService service;
    @MockBean
    private FuelConsumptionRepository repository;
    @MockBean
    private FuelTypeRepository fuelTypeRepository;

    @Test
    void create() {
        FuelConsumptionDto consumptionDto = new FuelConsumptionDto(FUEL_TYPE1.getId(), 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        FuelConsumption consumption = FuelConsumptionDto.getFuelConsumption(consumptionDto, FUEL_TYPE1);
        FuelConsumption expectedConsumption = new FuelConsumption(consumption);
        expectedConsumption.setId(1);
        when(repository.save(consumption)).thenReturn(expectedConsumption);
        when(fuelTypeRepository.findById(FUEL_TYPE1.getId())).thenReturn(Optional.of(FUEL_TYPE1));

        FuelConsumption createdConsumption = service.create(consumptionDto);

        verify(repository, times(1)).save(consumption);
        assertEquals(expectedConsumption, createdConsumption);
    }

    @Test
    void update() {
        FuelConsumptionDto consumptionDto = new FuelConsumptionDto(FUEL_TYPE1.getId(), 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        FuelConsumption consumption = FuelConsumptionDto.getFuelConsumption(consumptionDto, FUEL_TYPE1);
        FuelConsumption expectedConsumption = new FuelConsumption(consumption);
        expectedConsumption.setVolume(15.8);
        when(repository.save(consumption)).thenReturn(expectedConsumption);
        when(fuelTypeRepository.findById(FUEL_TYPE1.getId())).thenReturn(Optional.of(FUEL_TYPE1));

        FuelConsumption createdConsumption = service.update(consumptionDto);

        verify(repository, times(1)).save(consumption);
        assertEquals(expectedConsumption, createdConsumption);
    }

    @Test
    void delete() {
        FuelConsumption consumption = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        doNothing().when(repository).deleteById(consumption.getId());

        service.delete(consumption.getId());
        verify(repository, times(1)).deleteById(consumption.getId());
    }

    @Test
    void findAll() {
        FuelConsumption consumption1 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        FuelConsumption consumption2 = new FuelConsumption(2, FUEL_TYPE1, 12.1, 14.5, LocalDate.of(2019, 9, 21), 67891);
        List<FuelConsumption> expectedList = Arrays.asList(consumption1, consumption2);
        when(repository.findAll()).thenReturn(expectedList);

        List<FuelConsumption> resultList = service.findAll();

        verify(repository, times(1)).findAll();
        assertEquals(expectedList, resultList);
    }

    @Test
    void findByDriverId() {
        long SEARCH_DRIVER_ID = 12345;
        FuelConsumption consumption1 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        FuelConsumption consumption2 = new FuelConsumption(2, FUEL_TYPE1, 12.1, 14.5, LocalDate.of(2019, 9, 21), 12345);
        List<FuelConsumption> expectedList = Arrays.asList(consumption1, consumption2);
        when(repository.findByDriverId(SEARCH_DRIVER_ID)).thenReturn(expectedList);

        List<FuelConsumption> resultList = service.findByDriverId(SEARCH_DRIVER_ID);

        verify(repository, times(1)).findByDriverId(SEARCH_DRIVER_ID);
        assertEquals(expectedList, resultList);
    }

    @Test
    void sumSpentMoneyGroupByMonth() {
        FuelConsumption consumption1 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, 8, 21), 12345);
        FuelConsumption consumption2 = new FuelConsumption(2, FUEL_TYPE1, 12.1, 14.5, LocalDate.of(2019, 9, 21), 12345);
        MoneyByMonth moneyByMonth1 = new MoneyByMonth(8, consumption1.getVolume() * consumption1.getPrice());
        MoneyByMonth moneyByMonth2 = new MoneyByMonth(9, consumption2.getVolume() * consumption2.getPrice());
        List<MoneyByMonth> expectedList = Arrays.asList(moneyByMonth1, moneyByMonth2);
        when(repository.sumSpentMoneyGroupByMonth()).thenReturn(expectedList);

        List<MoneyByMonth> resultList = service.sumSpentMoneyGroupByMonth();

        verify(repository, times(1)).sumSpentMoneyGroupByMonth();
        assertEquals(expectedList, resultList);
    }

    @Test
    void sumSpentMoneyByDriverIdGroupByMonth() {
        long SEARCH_DRIVER_ID = 12345;
        FuelConsumption consumption1 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, 8, 21), 12345);
        FuelConsumption consumption2 = new FuelConsumption(2, FUEL_TYPE1, 12.1, 14.5, LocalDate.of(2019, 9, 21), 12345);
        MoneyByMonth moneyByMonth1 = new MoneyByMonth(8, consumption1.getVolume() * consumption1.getPrice());
        MoneyByMonth moneyByMonth2 = new MoneyByMonth(9, consumption2.getVolume() * consumption2.getPrice());
        List<MoneyByMonth> expectedList = Arrays.asList(moneyByMonth1, moneyByMonth2);
        when(repository.sumSpentMoneyByDriverIdGroupByMonth(SEARCH_DRIVER_ID)).thenReturn(expectedList);

        List<MoneyByMonth> resultList = service.sumSpentMoneyByDriverIdGroupByMonth(SEARCH_DRIVER_ID);

        verify(repository, times(1)).sumSpentMoneyByDriverIdGroupByMonth(SEARCH_DRIVER_ID);
        assertEquals(expectedList, resultList);
    }

    @Test
    void findFuelConsumptionByMonth() {
        int SEARCH_MONTH = 8;
        FuelConsumption consumption1 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, SEARCH_MONTH, 21), 12345);
        FuelConsumption consumption2 = new FuelConsumption(2, FUEL_TYPE1, 12.1, 14.5, LocalDate.of(2019, 9, 21), 12345);
        Utils.setTotalPrice(consumption1);
        Utils.setTotalPrice(consumption2);
        List<FuelConsumption> expectedList = Arrays.asList(consumption1, consumption2);
        when(repository.findFuelConsumptionByMonth(SEARCH_MONTH)).thenReturn(expectedList);

        List<FuelConsumption> resultList = service.findFuelConsumptionByMonth(SEARCH_MONTH);

        verify(repository, times(1)).findFuelConsumptionByMonth(SEARCH_MONTH);
        assertEquals(expectedList, resultList);
    }

    @Test
    void findFuelConsumptionByMonthAndByDriverId() {
        int SEARCH_MONTH = 8;
        long SEARCH_DRIVER_ID = 12345;
        FuelConsumption consumption1 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, SEARCH_MONTH, 21), SEARCH_DRIVER_ID);
        FuelConsumption consumption2 = new FuelConsumption(2, FUEL_TYPE1, 12.1, 14.5, LocalDate.of(2019, SEARCH_MONTH, 21), SEARCH_DRIVER_ID);
        Utils.setTotalPrice(consumption1);
        Utils.setTotalPrice(consumption2);
        List<FuelConsumption> expectedList = Arrays.asList(consumption1, consumption2);
        when(repository.findFuelConsumptionByMonthAndByDriverId(SEARCH_MONTH, SEARCH_DRIVER_ID)).thenReturn(expectedList);

        List<FuelConsumption> resultList = service.findFuelConsumptionByMonthAndByDriverId(SEARCH_MONTH, SEARCH_DRIVER_ID);

        verify(repository, times(1)).findFuelConsumptionByMonthAndByDriverId(SEARCH_MONTH, SEARCH_DRIVER_ID);
        assertEquals(expectedList, resultList);
    }

    @Test
    void findFuelConsumptionGroupByFuelType() {
        int MONTH = 8;
        FuelConsumption consumption1 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, MONTH, 21), 12345);
        FuelConsumption consumption2 = new FuelConsumption(2, FUEL_TYPE1, 12.1, 14.5, LocalDate.of(2019, MONTH, 21), 12345);
        FuelConsumptionStatistic fuelConsumptionStatistic = new FuelConsumptionStatistic(
                MONTH, FUEL_TYPE1,
                consumption1.getVolume() + consumption2.getVolume(),
                (consumption1.getPrice() + consumption2.getPrice()) / 2,
                consumption1.getPrice() * consumption1.getVolume() + consumption2.getPrice() * consumption2.getVolume());
        List<FuelConsumptionStatistic> expectedList = Arrays.asList(fuelConsumptionStatistic);
        when(repository.findFuelConsumptionGroupByFuelType()).thenReturn(expectedList);

        List<FuelConsumptionStatistic> resultList = service.findFuelConsumptionGroupByFuelType();

        verify(repository, times(1)).findFuelConsumptionGroupByFuelType();
        assertEquals(expectedList, resultList);
    }

    @Test
    void findFuelConsumptionByDriverIdGroupByFuelType() {
        long SEARCH_DRIVER_ID = 12345;
        int MONTH = 8;
        FuelConsumption consumption1 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, MONTH, 21), SEARCH_DRIVER_ID);
        FuelConsumption consumption2 = new FuelConsumption(2, FUEL_TYPE1, 12.1, 14.5, LocalDate.of(2019, MONTH, 21), SEARCH_DRIVER_ID);
        FuelConsumptionStatistic fuelConsumptionStatistic = new FuelConsumptionStatistic(
                MONTH, FUEL_TYPE1,
                consumption1.getVolume() + consumption2.getVolume(),
                (consumption1.getPrice() + consumption2.getPrice()) / 2,
                consumption1.getPrice() * consumption1.getVolume() + consumption2.getPrice() * consumption2.getVolume());
        List<FuelConsumptionStatistic> expectedList = Arrays.asList(fuelConsumptionStatistic);
        when(repository.findFuelConsumptionByDriverIdGroupByFuelType(SEARCH_DRIVER_ID)).thenReturn(expectedList);

        List<FuelConsumptionStatistic> resultList = service.findFuelConsumptionByDriverIdGroupByFuelType(SEARCH_DRIVER_ID);

        verify(repository, times(1)).findFuelConsumptionByDriverIdGroupByFuelType(SEARCH_DRIVER_ID);
        assertEquals(expectedList, resultList);
    }

}