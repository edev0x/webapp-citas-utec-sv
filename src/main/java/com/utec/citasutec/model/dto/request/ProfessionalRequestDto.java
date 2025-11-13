package com.utec.citasutec.model.dto.request;

import com.utec.citasutec.util.validators.ValidationConstants;
import com.utec.citasutec.util.validators.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfessionalRequestDto(
    Integer id,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @Size(
        min = ValidationConstants.MIN_NAME_LENGTH,
        max = ValidationConstants.MAX_NAME_LENGTH,
        message = ValidationMessages.NAME_LENGTH
    )
    String fullName,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @Email(message = ValidationMessages.INVALID_EMAIL_FORMAT)
    String email,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR)
    String specialty,
    boolean isActive
) {
}
