package com.utec.citasutec.util.validators.beans;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    private EnumValidator annotation;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
       this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        Enum<?>[] enumConstants = annotation.enumClass().getEnumConstants();

        return Arrays.stream(enumConstants).anyMatch(e -> e.name().equals(value) && (!annotation.ignoreCase() || e.name().equalsIgnoreCase(value)));
    }
}
