package com.utec.citasutec.model.dto.request;

import com.utec.citasutec.util.validators.ValidationMessages;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleRequestDto(
    @Nullable
    Integer id,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @Size(
        max = 20,
        min = 4,
        message = ValidationMessages.ROLE_MAX_LENGTH
    )
    String name
) {
}
