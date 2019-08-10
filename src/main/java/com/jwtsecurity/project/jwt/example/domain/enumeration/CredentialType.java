package com.jwtsecurity.project.jwt.example.domain.enumeration;

import java.util.regex.Pattern;

public enum CredentialType {
    LOGIN("^[a-zA-Z][\\w\\d]+$"),
    PASSWORD("[\\w\\d]+");
    private String regex;

    CredentialType(String regex) {
        this.regex = regex;
    }

    public Pattern getRegex() {
        return Pattern.compile(regex);
    }
}
