package com.jwtsecurity.project.jwt.example.service;

import com.jwtsecurity.project.jwt.example.config.security.JwtProvider;
import com.jwtsecurity.project.jwt.example.domain.dto.LoginDto;
import com.jwtsecurity.project.jwt.example.domain.repository.UserRepository;
import com.jwtsecurity.project.jwt.example.exceptions.AuthenticationException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserRepository userRepository;

    @Transactional
    public String createUserToken(LoginDto loginDto) {
        String login = loginDto.getLogin();
        if (userRepository.findByLogin(login).isPresent()) {
            try {
                return getAuthenticationToken(loginDto);
            } catch (BadCredentialsException e) {
                throw new AuthenticationException("Wrong Password");
            }
        } else {
            throw new AuthenticationException(
                    "User with login '" + login + "' wasn't found!");
        }
    }

    private String getAuthenticationToken(LoginDto loginDto) {
        String login = loginDto.getLogin();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, loginDto.getPassword()));
        Collection<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        return jwtProvider.createToken(login, roles);
    }
}
