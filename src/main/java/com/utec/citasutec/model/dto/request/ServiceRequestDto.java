package com.utec.citasutec.model.dto.request;

import com.utec.citasutec.util.validators.ValidationConstants;
import com.utec.citasutec.util.validators.ValidationMessages;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public record ServiceRequestDto(
    @Nullable
    Integer id,
    @NotBlank(
        message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC
    )
    @Size(
        min = ValidationConstants.MIN_SERVICE_NAME_LENGTH,
        max = ValidationConstants.MAX_SERVICE_NAME_LENGTH,
        message = ValidationMessages.SERVICE_NAME_LENGTH
    )
    String name,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    String description,
    LocalTime startTime,
    LocalTime endTime,
    @Nullable
    int professionalId
) {
}
