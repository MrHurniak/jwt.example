package com.jwtsecurity.project.jwt.example.domain.dto;

import com.jwtsecurity.project.jwt.example.domain.annotations.Credential;
import com.jwtsecurity.project.jwt.example.domain.enumeration.CredentialType;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class RegistrationDto {
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 100)
    @Credential(value = CredentialType.LOGIN, message = "Invalid login")
    private String login;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 255)
    @Credential(value = CredentialType.PASSWORD, message = "Invalid password")
    private String password;

    @Email
    @NotNull
    @Size(max = 255)
    @NotEmpty
    private String email;
}
