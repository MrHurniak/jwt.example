package com.jwtsecurity.project.jwt.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class NotUniqueLoginException extends RuntimeException {
    public NotUniqueLoginException(String login) {
        super("User with login '" + login + "' already exist");
    }
}
