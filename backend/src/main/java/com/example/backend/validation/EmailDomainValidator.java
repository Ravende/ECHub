package com.example.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailDomainValidator implements ConstraintValidator<ValidEmailDomain, String> {

    @Override
    public void initialize(ValidEmailDomain constraintAnnotation) {
    }

    // @ewhain.net으로 도메인 제한
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && email.endsWith("@ewhain.net");
    }
}