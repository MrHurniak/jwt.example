package com.jwtsecurity.project.jwt.example.controller;

import com.jwtsecurity.project.jwt.example.config.security.JwtProvider;
import com.jwtsecurity.project.jwt.example.domain.dto.LoginDto;
import com.jwtsecurity.project.jwt.example.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
public class LoginController {

  private UserService userService;

  @PostMapping("/login")
  public void login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
    response.setHeader(JwtProvider.TOKEN_HEADER,
            JwtProvider.TOKEN_PREFIX + userService.createUserToken(loginDto));
  }
}
