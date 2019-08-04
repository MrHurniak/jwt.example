package com.jwtsecurity.project.jwt.example.controller;

import com.jwtsecurity.project.jwt.example.domain.dto.ExpositionDto;
import com.jwtsecurity.project.jwt.example.domain.dto.HallDto;
import com.jwtsecurity.project.jwt.example.service.ExpositionService;
import com.jwtsecurity.project.jwt.example.service.HallService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
@AllArgsConstructor
public class PublicController {

    private HallService hallService;
    private ExpositionService expositionService;

    @GetMapping("/expositions")
    public List<ExpositionDto> getAllExpositions() {
        return expositionService.getAllExpositions();
    }

    @GetMapping("/exposition/{id}")
    public ExpositionDto getExpositionById(@PathVariable BigInteger id) {
        return expositionService.getExposition(id);
    }

    @GetMapping("/halls")
    public List<HallDto> getAllHalls() {
        return hallService.getAllHalls();
    }

    @GetMapping("/hall/{id}")
    public HallDto getHallById(@PathVariable BigInteger id) {
        return hallService.getHall(id);
    }
}
