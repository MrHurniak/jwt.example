package com.jwtsecurity.project.jwt.example.domain.annotations;

import com.jwtsecurity.project.jwt.example.domain.annotations.validators.CredentialValidator;
import com.jwtsecurity.project.jwt.example.domain.enumeration.CredentialType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CredentialValidator.class)
@Documented
public @interface Credential {
    CredentialType value();
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
