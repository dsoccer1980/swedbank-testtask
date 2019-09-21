package com.dsoccer1980.fuel.controller;

import com.dsoccer1980.fuel.domain.FuelConsumption;
import com.dsoccer1980.fuel.domain.FuelType;
import com.dsoccer1980.fuel.domain.dto.FuelConsumptionDto;
import com.dsoccer1980.fuel.service.FuelConsumptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
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
    @Autowired
    private MockMvc mvc;
    @MockBean
    private FuelConsumptionService fuelConsumptionService;

    @Test
    void findAll() throws Exception {
        FuelConsumption consumption1 = new FuelConsumption(1L, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        FuelConsumption consumption2 = new FuelConsumption(1L, FUEL_TYPE1, 10.1, 12.5, LocalDate.of(2019, 9, 21), 67891);
        List<FuelConsumption> expectedList = Arrays.asList(consumption1, consumption2);
        when(fuelConsumptionService.findAll()).thenReturn(expectedList);

        mvc.perform(get("/consumption"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(consumption1.getDriverId() + "")));
    }


    @Test
    void save() throws Exception {
        FuelConsumptionDto consumptionDto = new FuelConsumptionDto(FUEL_TYPE1.getId(), 10.1, 12.5, LocalDate.of(2019, 9, 21), 12345);
        FuelConsumption expectedConsumption = new FuelConsumption(1L, FUEL_TYPE1, 10.1, 19.5, LocalDate.of(2019, 9, 21), 12345);
        given(fuelConsumptionService.create(consumptionDto)).willReturn(expectedConsumption);

        mvc.perform(post("/consumption")
                .contentType(APPLICATION_JSON)
                .content("{\"fuelType\":\"1\",\"price\":\"10.1\",\"volume\":\"12.5\",\"date\":\"2019-09-21\",\"driverId\":\"12345\"}"))
                .andExpect(status().isOk());

    }
}