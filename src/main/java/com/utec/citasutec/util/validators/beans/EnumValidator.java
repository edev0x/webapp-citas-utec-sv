package com.utec.citasutec.util.validators.beans;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidatorImpl.class)
public @interface EnumValidator {
    String message() default "Value is not valid for enum. Allowed values:";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Enum<?>> enumClass();
    boolean ignoreCase() default false;
}
