package com.jwtsecurity.project.jwt.example.controller;

import com.jwtsecurity.project.jwt.example.domain.dto.ExpositionDto;
import com.jwtsecurity.project.jwt.example.domain.dto.HallDto;
import com.jwtsecurity.project.jwt.example.service.ExpositionService;
import com.jwtsecurity.project.jwt.example.service.HallService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private ExpositionService expositionService;
    private HallService hallService;

    @PostMapping("/exposition")
    public ExpositionDto createExposition(@RequestBody ExpositionDto exposition) {
        return expositionService.save(exposition);
    }

    @PostMapping("/hall")
    public HallDto createHall(@RequestBody HallDto hall) {
        return hallService.save(hall);
    }

    @PutMapping("/hall/{id}")
    public HallDto updateHall(@PathVariable BigInteger id, @RequestBody HallDto hall) {
        return hallService.update(id, hall);
    }

    @PutMapping("/exposition/{id}")
    public ExpositionDto updateExposition(@PathVariable BigInteger id, @RequestBody ExpositionDto exposition) {
        return expositionService.update(id, exposition);
    }

    @DeleteMapping("/hall/{id}")
    public void deleteHall(@PathVariable BigInteger id) {
        hallService.delete(id);
    }

    @DeleteMapping("/exposition/{id}")
    public void deleteExposition(@PathVariable BigInteger id) {
        expositionService.delete(id);
    }

}
