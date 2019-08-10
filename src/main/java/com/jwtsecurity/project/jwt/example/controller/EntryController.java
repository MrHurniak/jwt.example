package com.jwtsecurity.project.jwt.example.controller;

import com.jwtsecurity.project.jwt.example.config.security.JwtProvider;
import com.jwtsecurity.project.jwt.example.domain.dto.LoginDto;
import com.jwtsecurity.project.jwt.example.domain.dto.RegistrationDto;
import com.jwtsecurity.project.jwt.example.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@AllArgsConstructor
public class EntryController {

    private UserService userService;

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        response.setHeader(JwtProvider.TOKEN_HEADER,
                JwtProvider.TOKEN_PREFIX + userService.createUserToken(loginDto));
    }

    @PostMapping("/registration")
    public ResponseEntity register(@Valid @RequestBody RegistrationDto registrationDto) {
        userService.registerUser(registrationDto);
        return ResponseEntity.ok(Collections.singletonMap("message", "Successfully registered"));
    }
}
