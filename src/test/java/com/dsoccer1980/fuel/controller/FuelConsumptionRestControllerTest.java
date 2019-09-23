package com.dsoccer1980.fuel.controller;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.FuelType;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionDto;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionStatistic;
import com.dsoccer1980.fuel.domain.dto.MoneyByMonth;
import com.dsoccer1980.fuel.service.FuelConsumptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({FuelConsumptionRestController.class})
class FuelConsumptionRestControllerTest {

    private final FuelType FUEL_TYPE1 = new FuelType(1, "95");
    private final FuelType FUEL_TYPE2 = new FuelType(2, "98");
    @Autowired
    private MockMvc mvc;
    @MockBean
    private FuelConsumptionService fuelConsumptionService;

    @Test
    void save() throws Exception {
        FuelConsumptionDto consumptionDto = new FuelConsumptionDto(FUEL_TYPE1.getId(), 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        FuelConsumption expectedConsumption = new FuelConsumption(1, FUEL_TYPE1, 10.1, 19.5, LocalDate.of(2019, 9, 21), 12345);
        when(fuelConsumptionService.create(consumptionDto)).thenReturn(expectedConsumption);
        String json = new ObjectMapper().writeValueAsString(consumptionDto);

        mvc.perform(post("/consumption")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void sumSpentMoneyGroupByMonth() throws Exception {
        MoneyByMonth moneyByMonth1 = new MoneyByMonth(8, 450);
        MoneyByMonth moneyByMonth2 = new MoneyByMonth(9, 550);
        when(fuelConsumptionService.sumSpentMoneyGroupByMonth()).thenReturn(Arrays.asList(moneyByMonth1, moneyByMonth2));

        mvc.perform(get("/consumption/money"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"month\":8,\"sum\":450.0},{\"month\":9,\"sum\":550.0}]")));
    }

    @Test
    void sumSpentMoneyByDriverIdGroupByMonth() throws Exception {
        MoneyByMonth moneyByMonth1 = new MoneyByMonth(8, 450);
        MoneyByMonth moneyByMonth2 = new MoneyByMonth(9, 550);
        when(fuelConsumptionService.sumSpentMoneyByDriverIdGroupByMonth(1111)).thenReturn(Arrays.asList(moneyByMonth1, moneyByMonth2));

        mvc.perform(get("/consumption/money/driver/1111"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"month\":8,\"sum\":450.0},{\"month\":9,\"sum\":550.0}]")));
    }

    @Test
    void findFuelConsumptionByMonth() throws Exception {
        FuelConsumption consumption1 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 2.5, LocalDate.of(2019, 8, 22), 1111);
        FuelConsumption consumption2 = new FuelConsumption(3, FUEL_TYPE2, 12.1, 4.5, LocalDate.of(2019, 8, 21), 2222);
        when(fuelConsumptionService.findFuelConsumptionByMonth(8)).thenReturn(Arrays.asList(consumption1, consumption2));

        mvc.perform(get("/consumption/month/8"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "[{\"id\":1,\"fuelType\":{\"id\":1,\"type\":\"95\"},\"price\":10.1,\"volume\":2.5,\"date\":\"2019-08-22\",\"driverId\":1111}," +
                                "{\"id\":3,\"fuelType\":{\"id\":2,\"type\":\"98\"},\"price\":12.1,\"volume\":4.5,\"date\":\"2019-08-21\",\"driverId\":2222}]"
                )));
    }

    @Test
    void findFuelConsumptionByMonthAndByDriverId() throws Exception {
        FuelConsumption consumption1 = new FuelConsumption(1, FUEL_TYPE1, 10.1, 2.5, LocalDate.of(2019, 8, 22), 1111);
        FuelConsumption consumption2 = new FuelConsumption(2, FUEL_TYPE2, 12.1, 4.5, LocalDate.of(2019, 8, 21), 1111);
        when(fuelConsumptionService.findFuelConsumptionByMonthAndByDriverId(8, 1111)).thenReturn(Arrays.asList(consumption1, consumption2));

        mvc.perform(get("/consumption/month/8/driver/1111"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "[{\"id\":1,\"fuelType\":{\"id\":1,\"type\":\"95\"},\"price\":10.1,\"volume\":2.5,\"date\":\"2019-08-22\",\"driverId\":1111}," +
                                "{\"id\":2,\"fuelType\":{\"id\":2,\"type\":\"98\"},\"price\":12.1,\"volume\":4.5,\"date\":\"2019-08-21\",\"driverId\":1111}]"
                )));
    }

    @Test
    void findFuelConsumptionGroupByFuelType() throws Exception {
        FuelConsumptionStatistic fuelConsumptionStatistic = new FuelConsumptionStatistic(
                8, FUEL_TYPE1, 450, 13.5, 6075);
        when(fuelConsumptionService.findFuelConsumptionGroupByFuelType()).thenReturn(Collections.singletonList(fuelConsumptionStatistic));

        mvc.perform(get("/consumption/statistic"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "[{\"month\":8,\"fuelType\":{\"id\":1,\"type\":\"95\"},\"volume\":450.0,\"averagePrice\":13.5,\"total\":6075.0}]")));
    }

    @Test
    void findFuelConsumptionByDriverIdGroupByFuelType() throws Exception {
        FuelConsumptionStatistic fuelConsumptionStatistic = new FuelConsumptionStatistic(
                8, FUEL_TYPE1, 450, 13.5, 6075);
        when(fuelConsumptionService.findFuelConsumptionByDriverIdGroupByFuelType(1111)).thenReturn(Collections.singletonList(fuelConsumptionStatistic));

        mvc.perform(get("/consumption/statistic/driver/1111"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "[{\"month\":8,\"fuelType\":{\"id\":1,\"type\":\"95\"},\"volume\":450.0,\"averagePrice\":13.5,\"total\":6075.0}]")));
    }
}