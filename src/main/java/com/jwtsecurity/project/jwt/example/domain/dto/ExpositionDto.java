package com.jwtsecurity.project.jwt.example.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class ExpositionDto {
    private BigInteger id;
    private String name;
    private long price;
    private String begin;
    private String end;
    private HallDto hall;
}
