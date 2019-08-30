package com.jwtsecurity.project.jwt.example.controller;

import com.google.common.collect.ImmutableList;
import com.jwtsecurity.project.jwt.example.domain.dto.ExpositionDto;
import com.jwtsecurity.project.jwt.example.domain.dto.HallDto;
import com.jwtsecurity.project.jwt.example.service.ExpositionService;
import com.jwtsecurity.project.jwt.example.service.HallService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PublicControllerTest {
    @Autowired private MockMvc mvc;

    @MockBean private HallService hallService;
    @MockBean private ExpositionService expositionService;
    @InjectMocks private static PublicController controller;

    private static final BigInteger HALL_ID = BigInteger.ONE;
    private static final String HALL_NAME = "Hall#1";
    private static final String HALL_ADDRESS = "Address";
    private static final BigInteger EXPOSITION_ID = BigInteger.TEN;
    private static final String EXPOSITION_NAME = "Exposition#1";
    private static final long EXPOSITION_PRICE = 1_000L;
    private static final LocalDate EXPOSITION_BEGIN = LocalDate.of(2019, 1, 5);
    private static final LocalDate EXPOSITION_END = LocalDate.of(2019, 5, 1);

    private static ExpositionDto expositionDto;
    private static HallDto hallDto;

    @BeforeAll
    static void setUp() {
        hallDto = new HallDto().setId(HALL_ID)
                .setName(HALL_NAME)
                .setAddress(HALL_ADDRESS);
        expositionDto = new ExpositionDto().setId(EXPOSITION_ID)
                .setName(EXPOSITION_NAME)
                .setPrice(EXPOSITION_PRICE)
                .setBegin(EXPOSITION_BEGIN.toString())
                .setEnd(EXPOSITION_END.toString())
                .setHall(hallDto);
    }

    @Test
    void getAllExpositions() throws Exception {
        when(expositionService.getAllExpositions()).thenReturn(ImmutableList.of(expositionDto));

        mvc.perform(get("/expositions"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(EXPOSITION_ID))
                .andExpect(jsonPath("$.[0].name").value(EXPOSITION_NAME))
                .andExpect(jsonPath("$.[0].price").value(EXPOSITION_PRICE))
                .andExpect(jsonPath("$.[0].begin").value(EXPOSITION_BEGIN.toString()))
                .andExpect(jsonPath("$.[0].end").value(EXPOSITION_END.toString()))
                .andExpect(jsonPath("$.[0].hall.id").value(HALL_ID))
                .andExpect(jsonPath("$.[0].hall.name").value(HALL_NAME))
                .andExpect(jsonPath("$.[0].hall.address").value(HALL_ADDRESS))
                .andDo(print());

        verify(expositionService).getAllExpositions();
    }

    @Test
    void getExpositionByIdWhenNoSuchExposition() throws Exception {
        mvc.perform(get("/exposition/{id}", EXPOSITION_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());

        verify(expositionService).getExposition(any(BigInteger.class));
    }

    @Test
    void getAllHalls() throws Exception {
        when(hallService.getAllHalls()).thenReturn(ImmutableList.of(hallDto));

        mvc.perform(get("/halls"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(HALL_ID))
                .andExpect(jsonPath("$.[0].name").value(HALL_NAME))
                .andExpect(jsonPath("$.[0].address").value(HALL_ADDRESS))
                .andDo(print());

        verify(hallService).getAllHalls();
    }

    @Test
    void getHallByIdWhenSuchHallExists() throws Exception {
        when(hallService.getHall(any(BigInteger.class))).thenReturn(hallDto);

        mvc.perform(get("/hall/{id}", HALL_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(HALL_ID))
                .andExpect(jsonPath("$.name").value(HALL_NAME))
                .andExpect(jsonPath("$.address").value(HALL_ADDRESS))
                .andDo(print());

        verify(hallService).getHall(any(BigInteger.class));
    }
}