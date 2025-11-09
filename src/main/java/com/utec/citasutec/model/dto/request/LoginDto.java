package com.utec.citasutec.model.dto.request;

import com.utec.citasutec.util.validators.ValidationMessages;
import com.utec.citasutec.util.validators.beans.StrongPassword;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    String email,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @StrongPassword(message = ValidationMessages.INVALID_PASSWORD_FORMAT)
    String password,
    Boolean rememberMe) {
}
