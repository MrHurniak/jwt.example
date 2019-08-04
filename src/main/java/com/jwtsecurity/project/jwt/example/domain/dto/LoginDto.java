package com.jwtsecurity.project.jwt.example.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginDto {

  private String login;
  private String password;
}
