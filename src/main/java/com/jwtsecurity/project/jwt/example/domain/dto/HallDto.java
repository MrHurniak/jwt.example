package com.jwtsecurity.project.jwt.example.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class HallDto {
    private BigInteger id;
    private String name;
    private String address;
}
