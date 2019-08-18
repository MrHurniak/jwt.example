package com.jwtsecurity.project.jwt.example.service;

import com.google.common.collect.ImmutableList;
import com.jwtsecurity.project.jwt.example.domain.dto.HallDto;
import com.jwtsecurity.project.jwt.example.domain.entity.Hall;
import com.jwtsecurity.project.jwt.example.domain.mappers.HallMapper;
import com.jwtsecurity.project.jwt.example.domain.repository.HallRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HallServiceTest {

    @InjectMocks private HallService service;
    @Mock private HallRepository repository;
    @Spy  private HallMapper mapper = new HallMapper();

    private static final BigInteger HALL_ID = BigInteger.ONE;
    private static final String HALL_NAME = "Hall#1";
    private static final String HALL_ADDRESS = "Address";

    private static Hall hall;

    @BeforeAll
    static void setUp() {
        hall = new Hall().setId(HALL_ID)
                .setName(HALL_NAME)
                .setAddress(HALL_ADDRESS);
    }

    @Test
    void tryGetAllHalls() {
        when(repository.findAll()).thenReturn(ImmutableList.of(hall));

        List<HallDto> hallsFromDB = service.getAllHalls();

        assertEquals(ImmutableList.of(mapper.hallToDto(hall)), hallsFromDB);
        verify(mapper, atLeastOnce()).dtoToHall(any());
        verify(mapper, atLeastOnce()).hallToDto(any());
        verify(repository).findAll();
    }

    @Test
    void tryGetHallById() {
        when(repository.getOne(any(BigInteger.class))).thenReturn(hall);

        HallDto hallFromDB = service.getHall(HALL_ID);

        assertEquals(mapper.hallToDto(hall), hallFromDB);
        verify(mapper, atLeastOnce()).hallToDto(any());
        verify(repository).getOne(eq(HALL_ID));
    }

    @Test
    void trySaveHall() {

    }

}