package com.utec.citasutec.model.dto.request;

import com.utec.citasutec.util.validators.ValidationMessages;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfessionalServiceRequestDto (
    @Nullable
    Integer id,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    String serviceName,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    String serviceDescription,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    String date,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    String startTime,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    String endTime,
    @NotNull(message = ValidationMessages.PROFESSIONAL_ID_REQUIRED)
    Integer professionalId
) {
}
