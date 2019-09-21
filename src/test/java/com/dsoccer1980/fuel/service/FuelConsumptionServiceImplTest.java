package com.dsoccer1980.fuel.service;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.FuelType;
import com.dsoccer1980.fuel.repository.FuelConsumptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void create() {
        FuelConsumption consumption = new FuelConsumption(FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        FuelConsumption expectedConsumption = new FuelConsumption(consumption);
        expectedConsumption.setId(1);
        when(repository.save(consumption)).thenReturn(expectedConsumption);

        FuelConsumption createdConsumption = service.create(consumption);

        verify(repository, times(1)).save(consumption);
        assertEquals(expectedConsumption, createdConsumption);
    }

    @Test
    void update() {
        FuelConsumption consumption = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        FuelConsumption expectedConsumption = new FuelConsumption(consumption);
        expectedConsumption.setVolume(15.8);
        when(repository.save(consumption)).thenReturn(expectedConsumption);

        FuelConsumption createdConsumption = service.update(consumption);

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
        FuelConsumption consumption2 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, 9, 21), 67891);
        List<FuelConsumption> expectedList = Arrays.asList(consumption1, consumption2);
        when(repository.findAll()).thenReturn(expectedList);

        List<FuelConsumption> resultList = service.findAll();

        verify(repository, times(1)).findAll();
        assertEquals(expectedList, resultList);
    }

    @Test
    void findByDriverId() {
        FuelConsumption consumption1 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        FuelConsumption consumption2 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        List<FuelConsumption> expectedList = Arrays.asList(consumption1, consumption2);
        when(repository.findByDriverId(consumption1.getId())).thenReturn(expectedList);

        List<FuelConsumption> resultList = service.findByDriverId(consumption1.getId());

        verify(repository, times(1)).findByDriverId(consumption1.getId());
        assertEquals(expectedList, resultList);
    }
}