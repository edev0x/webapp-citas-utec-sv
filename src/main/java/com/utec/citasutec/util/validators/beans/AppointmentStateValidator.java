package com.utec.citasutec.util.validators.beans;

import com.utec.citasutec.util.AppointmentState;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppointmentStateValidator implements ConstraintValidator<ValidAppointmentState, String> {
    private String validationMessage;

    @Override
    public void initialize(ValidAppointmentState constraintAnnotation) {
        this.validationMessage = Stream.of(AppointmentState.values())
            .map(AppointmentState::getTranslation)
            .collect(Collectors.joining(", ", "[", "]"));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        boolean isValid = Stream.of(AppointmentState.values())
            .map(AppointmentState::getTranslation)
            .anyMatch(value::equals);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                context.getDefaultConstraintMessageTemplate() + " " + validationMessage
            ).addConstraintViolation();
        }

        return isValid;
    }
}
