package com.jwtsecurity.project.jwt.example.service;

import com.jwtsecurity.project.jwt.example.domain.dto.HallDto;
import com.jwtsecurity.project.jwt.example.domain.mappers.HallMapper;
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
public class HallService {
    private HallRepository repository;
    private HallMapper mapper;

    public List<HallDto> getAllHalls() {
        return repository.findAll().stream().map(mapper::hallToDto).collect(Collectors.toList());
    }

    public HallDto getHall(BigInteger id) {
        return mapper.hallToDto(repository.getOne(id));
    }

    public HallDto save(HallDto hall) {
        return mapper.hallToDto(repository.save(mapper.dtoToHall(hall)));
    }

    public HallDto update(BigInteger id, HallDto hall) {
        return mapper.hallToDto(repository.save(mapper.dtoToHall(hall.setId(id))));
    }

    public void delete(BigInteger id) {
        repository.deleteById(id);
    }
}
