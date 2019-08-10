package com.jwtsecurity.project.jwt.example.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class LoginDto {
  @NotNull
  @NotEmpty
  @Size(max = 100)
  private String login;

  @NotNull
  @NotEmpty
  @Size(max = 255)
  private String password;
}
