package com.utec.citasutec.util.formatters;

import jakarta.validation.ConstraintViolation;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ConstraintFormatter {
    private ConstraintFormatter() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> Map<String, String> getValidationErrors(Set<ConstraintViolation<T>> constraintViolations) {
        if (constraintViolations == null) {
            return Collections.emptyMap();
        }

        return constraintViolations.stream()
            .collect(Collectors.toMap(
                v -> v.getPropertyPath().toString(),
                ConstraintViolation::getMessage,
                (existing, replacement) -> existing
            )
        );
    }
}