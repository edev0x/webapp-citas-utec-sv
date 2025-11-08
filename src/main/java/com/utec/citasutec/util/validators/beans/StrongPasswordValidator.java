package com.utec.citasutec.util.validators.beans;

import com.utec.citasutec.util.validators.ValidationConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(s)) return false;

        return s.matches(ValidationConstants.PASSWORD_PATTERN);
    }
}
