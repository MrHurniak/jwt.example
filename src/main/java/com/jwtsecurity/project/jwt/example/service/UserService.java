package com.jwtsecurity.project.jwt.example.service;

import com.jwtsecurity.project.jwt.example.config.security.JwtProvider;
import com.jwtsecurity.project.jwt.example.domain.dto.LoginDto;
import com.jwtsecurity.project.jwt.example.domain.dto.RegistrationDto;
import com.jwtsecurity.project.jwt.example.domain.entity.User;
import com.jwtsecurity.project.jwt.example.domain.enumeration.Role;
import com.jwtsecurity.project.jwt.example.domain.mappers.UserMapper;
import com.jwtsecurity.project.jwt.example.domain.repository.UserRepository;
import com.jwtsecurity.project.jwt.example.exceptions.AuthenticationException;
import com.jwtsecurity.project.jwt.example.exceptions.NotUniqueLoginException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    @Transactional
    public String createUserToken(LoginDto userDto) {
        String login = userDto.getLogin();
        if (userRepository.findByLogin(login).isPresent()) {
            try {
                return getAuthenticationToken(userDto);
            } catch (BadCredentialsException e) {
                throw new AuthenticationException("Wrong password!");
            }
        } else {
            throw new AuthenticationException("User with login '" + login + "' wasn't found!");
        }
    }

    @Transactional
    public void registerUser(RegistrationDto userDto) {
        String login = userDto.getLogin();
        if (userRepository.findByLogin(login).isPresent()) {
            throw new NotUniqueLoginException(login);
        }
        User user = createSimpleUserFromDto(userDto);
        userRepository.save(user);
    }

    private String getAuthenticationToken(LoginDto userDto) {
        String login = userDto.getLogin();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, userDto.getPassword()));
        Collection<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        return jwtProvider.createToken(login, roles);
    }

    private User createSimpleUserFromDto(RegistrationDto userDto) {
        User user = userMapper.dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        return user;
    }
}
