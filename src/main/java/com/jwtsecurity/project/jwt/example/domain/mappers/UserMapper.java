package com.jwtsecurity.project.jwt.example.domain.mappers;

import com.jwtsecurity.project.jwt.example.domain.dto.LoginDto;
import com.jwtsecurity.project.jwt.example.domain.dto.RegistrationDto;
import com.jwtsecurity.project.jwt.example.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User dtoToUser(LoginDto dto) {
        return new User()
                .setLogin(dto.getLogin())
                .setPassword(dto.getPassword());
    }

    public User dtoToUser(RegistrationDto dto) {
        return new User()
                .setLogin(dto.getLogin())
                .setPassword(dto.getPassword())
                .setEmail(dto.getEmail());
    }
}
