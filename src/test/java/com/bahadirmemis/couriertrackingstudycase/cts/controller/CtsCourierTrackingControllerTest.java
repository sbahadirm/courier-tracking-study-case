package com.bahadirmemis.couriertrackingstudycase.cts.controller;

import com.bahadirmemis.couriertrackingstudycase.BaseTest;
import com.bahadirmemis.couriertrackingstudycase.cts.dto.CtsCourierLocationInfoDto;
import com.bahadirmemis.couriertrackingstudycase.cts.dto.CtsCourierSaveRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CtsCourierTrackingControllerTest extends BaseTest {

    private static final String BASE_PATH = "/api/v1/couriers";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void saveCourier() throws Exception {

        CtsCourierSaveRequestDto cusCustomerSaveRequestDto = CtsCourierSaveRequestDto.builder()
                .name("Yusuf")
                .build();

        String content = objectMapper.writeValueAsString(cusCustomerSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void saveCourierPosition() throws Exception {

        CtsCourierLocationInfoDto cusCustomerSaveRequestDto = CtsCourierLocationInfoDto.builder()
                .courierId(1L)
                .time(LocalDateTime.of(2022,4, 5, 12,0,0))
                .latitude(40.986106)
                .longitude(29.1161293)
                .build();

        String content = objectMapper.writeValueAsString(cusCustomerSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH + "/positions").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void getTotalDistanceOfCourier() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_PATH+ "/total-distance/1")
                        .content("1L")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }
}