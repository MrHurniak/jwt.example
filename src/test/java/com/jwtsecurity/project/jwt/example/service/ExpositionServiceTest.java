package com.jwtsecurity.project.jwt.example.service;

import com.google.common.collect.ImmutableList;
import com.jwtsecurity.project.jwt.example.domain.dto.ExpositionDto;
import com.jwtsecurity.project.jwt.example.domain.dto.HallDto;
import com.jwtsecurity.project.jwt.example.domain.entity.Exposition;
import com.jwtsecurity.project.jwt.example.domain.entity.Hall;
import com.jwtsecurity.project.jwt.example.domain.mappers.ExpositionMapper;
import com.jwtsecurity.project.jwt.example.domain.repository.ExpositionRepository;
import com.jwtsecurity.project.jwt.example.domain.repository.HallRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExpositionServiceTest {

    @InjectMocks private ExpositionService service;
    @Spy  private ExpositionMapper mapper = Mappers.getMapper(ExpositionMapper.class);
    @Mock private ExpositionRepository repository;
    @Mock private HallRepository hallRepository;

    private static final BigInteger HALL_ID = BigInteger.ONE;
    private static final String HALL_NAME = "Hall#1";
    private static final String HALL_ADDRESS = "Address";
    private static final BigInteger EXPOSITION_ID = BigInteger.TEN;
    private static final String EXPOSITION_NAME = "Exposition#1";
    private static final long EXPOSITION_PRICE = 1_000L;
    private static final LocalDate EXPOSITION_BEGIN = LocalDate.of(2019, 1, 5 );
    private static final LocalDate EXPOSITION_END = LocalDate.of(2019, 5, 1);

    private static Exposition exposition;
    private static Hall hall;
    private static ExpositionDto expositionDto;
    private static HallDto hallDto;

    @BeforeAll
    static void setUp() {
        hall = new Hall().setId(HALL_ID)
                .setName(HALL_NAME)
                .setAddress(HALL_ADDRESS);
        exposition = new Exposition().setId(EXPOSITION_ID)
                .setName(EXPOSITION_NAME)
                .setPrice(EXPOSITION_PRICE)
                .setHall(hall)
                .setBegin(EXPOSITION_BEGIN)
                .setEnd(EXPOSITION_END);
        hallDto = new HallDto().setId(HALL_ID)
                .setName(HALL_NAME)
                .setAddress(HALL_ADDRESS);
        expositionDto = new ExpositionDto().setId(EXPOSITION_ID)
                .setName(EXPOSITION_NAME)
                .setPrice(EXPOSITION_PRICE)
                .setBegin(EXPOSITION_BEGIN.toString())
                .setEnd(EXPOSITION_END.toString());
    }

    @Test
    void tryGetAllExpositions() {
        when(repository.findAll()).thenReturn(ImmutableList.of(exposition));

        List<ExpositionDto> allExpositions = service.getAllExpositions();

        assertEquals(ImmutableList.of(mapper.expositionToDto(exposition)), allExpositions);
        verify(mapper, atLeastOnce()).expositionToDto(any(Exposition.class));
        verify(repository).findAll();
    }

    @Test
    void tryGetExpositionById() {
        when(repository.getOne(any(BigInteger.class))).thenReturn(exposition);

        ExpositionDto expositionFromDB = service.getExposition(EXPOSITION_ID);

        assertEquals(mapper.expositionToDto(exposition), expositionFromDB);
        verify(mapper, atLeastOnce()).expositionToDto(any(Exposition.class));
    }

    @Test
    void tryDeleteByID() {
        ArgumentCaptor<BigInteger> idCaptor = ArgumentCaptor.forClass(BigInteger.class);

        service.delete(EXPOSITION_ID);

        verify(repository).deleteById(idCaptor.capture());
        assertEquals(EXPOSITION_ID, idCaptor.getValue());
    }

    @Test
    void trySaveExpositionWithHalId() {
        expositionDto.setHall(hallDto);
        when(hallRepository.getOne(any(BigInteger.class))).thenReturn(hall);

        service.save(expositionDto);

        verify(mapper, atLeastOnce()).expositionToDto(any());
        verify(mapper, atLeastOnce()).dtoToExposition(any());
        verify(hallRepository, never()).save(any());
        verify(hallRepository).getOne(eq(HALL_ID));
        verify(repository).save(any(Exposition.class));
    }

    @Test
    void trySaveExpositionWithNewHall() {
        expositionDto.setHall(hallDto.setId(null));
        when(hallRepository.save(any(Hall.class))).thenReturn(hall);

        service.save(expositionDto);

        verify(mapper, atLeastOnce()).expositionToDto(any());
        verify(mapper, atLeastOnce()).dtoToExposition(any());
        verify(hallRepository).save(any(Hall.class));
        verify(hallRepository, never()).getOne(any());
        verify(repository).save(any(Exposition.class));
    }

    @Test
    void tryUpdateExpositionWithHalId() {
        expositionDto.setHall(hallDto);
        when(hallRepository.getOne(any(BigInteger.class))).thenReturn(hall);

        service.update(EXPOSITION_ID, expositionDto);

        verify(mapper, atLeastOnce()).expositionToDto(any());
        verify(mapper, atLeastOnce()).dtoToExposition(any());
        verify(hallRepository, never()).save(any());
        verify(hallRepository).getOne(eq(HALL_ID));
        verify(repository).save(any(Exposition.class));
    }

    @Test
    void tryUpdateExpositionWithNewHall() {
        expositionDto.setHall(hallDto.setId(null));
        when(hallRepository.save(any(Hall.class))).thenReturn(hall);

        service.update(EXPOSITION_ID, expositionDto);

        verify(mapper, atLeastOnce()).expositionToDto(any());
        verify(mapper, atLeastOnce()).dtoToExposition(any());
        verify(hallRepository).save(any(Hall.class));
        verify(hallRepository, never()).getOne(any());
        verify(repository).save(any(Exposition.class));
    }
}