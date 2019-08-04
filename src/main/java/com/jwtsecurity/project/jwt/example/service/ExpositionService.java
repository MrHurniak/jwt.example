package com.jwtsecurity.project.jwt.example.service;

import com.jwtsecurity.project.jwt.example.domain.dto.ExpositionDto;
import com.jwtsecurity.project.jwt.example.domain.entity.Exposition;
import com.jwtsecurity.project.jwt.example.domain.entity.Hall;
import com.jwtsecurity.project.jwt.example.domain.mappers.ExpositionMapper;
import com.jwtsecurity.project.jwt.example.domain.repository.ExpositionRepository;
import com.jwtsecurity.project.jwt.example.domain.repository.HallRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ExpositionService {
    private ExpositionMapper mapper;
    private ExpositionRepository repository;
    private HallRepository hallRepository;

    public List<ExpositionDto> getAllExpositions() {
        return repository.findAll().stream().map(mapper::expositionToDto).collect(Collectors.toList());
    }

    public ExpositionDto getExposition(BigInteger id) {
        return mapper.expositionToDto(repository.getOne(id));
    }

    public ExpositionDto save(ExpositionDto expositionDto) {
        Exposition exposition = checkHall(mapper.dtoToExposition(expositionDto));
        return mapper.expositionToDto(repository.save(exposition));
    }

    public ExpositionDto update(BigInteger id, ExpositionDto expositionDto) {
        Exposition exposition = checkHall(mapper.dtoToExposition(expositionDto.setId(id)));
        return mapper.expositionToDto(repository.save(exposition));
    }

    public void delete(BigInteger id) {
        repository.deleteById(id);
    }

    private Exposition checkHall(Exposition exposition) {
        Hall hall;
        BigInteger id = exposition.getHall().getId();
        if(id != null) {
            hall = hallRepository.getOne(id);
        } else {
            hall = hallRepository.save(exposition.getHall());
        }
        exposition.setHall(hall);
        return exposition;
    }
}
