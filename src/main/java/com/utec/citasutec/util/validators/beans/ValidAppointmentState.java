package com.utec.citasutec.util.validators.beans;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AppointmentStateValidator.class)
public @interface ValidAppointmentState {
    String message() default "Invalid appointment state type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
