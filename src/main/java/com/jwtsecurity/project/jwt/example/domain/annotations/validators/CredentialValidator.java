package com.jwtsecurity.project.jwt.example.domain.annotations.validators;

import com.jwtsecurity.project.jwt.example.domain.annotations.Credential;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CredentialValidator implements ConstraintValidator<Credential, String> {
    private Pattern pattern;

    @Override
    public void initialize(Credential constraintAnnotation) {
        pattern = constraintAnnotation.value().getRegex();
    }

    @Override
    public boolean isValid(String credential, ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(credential).matches();
    }
}