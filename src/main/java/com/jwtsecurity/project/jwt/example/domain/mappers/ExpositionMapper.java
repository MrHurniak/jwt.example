package com.jwtsecurity.project.jwt.example.domain.mappers;

import com.jwtsecurity.project.jwt.example.domain.dto.ExpositionDto;
import com.jwtsecurity.project.jwt.example.domain.entity.Exposition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ExpositionMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "begin", source = "begin", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "end", source = "end", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "hall", source = "hall")
    })
    ExpositionDto expositionToDto(Exposition exposition);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "begin", source = "begin", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "end", source = "end", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "hall", source = "hall")
    })
    Exposition dtoToExposition(ExpositionDto dto);

}
