package com.jwtsecurity.project.jwt.example.domain.mappers;

import com.jwtsecurity.project.jwt.example.domain.dto.HallDto;
import com.jwtsecurity.project.jwt.example.domain.entity.Hall;
import org.springframework.stereotype.Component;

@Component
public class HallMapper {
    public HallDto hallToDto(Hall hall){
        return new HallDto()
                .setId(hall.getId())
                .setName(hall.getName())
                .setAddress(hall.getAddress());
    }

    public Hall dtoToHall(HallDto dto){
        return new Hall()
                .setId(dto.getId())
                .setName(dto.getName())
                .setAddress(dto.getAddress());
    }
}
