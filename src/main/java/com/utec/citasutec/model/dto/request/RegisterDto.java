package com.utec.citasutec.model.dto.request;

import com.utec.citasutec.util.validators.ValidationConstants;
import com.utec.citasutec.util.validators.ValidationMessages;
import com.utec.citasutec.util.validators.beans.StrongPassword;
import jakarta.validation.constraints.*;

public record RegisterDto(
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @Size(
        min = ValidationConstants.MIN_NAME_LENGTH,
        max = ValidationConstants.MAX_NAME_LENGTH,
        message = ValidationMessages.NAME_LENGTH
    )
    String firstName,

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @Size(
        min = ValidationConstants.MIN_NAME_LENGTH,
        max = ValidationConstants.MAX_NAME_LENGTH,
        message = ValidationMessages.LAST_NAME_LENGTH
    )
    String lastName,

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @Email(message = ValidationMessages.INVALID_EMAIL_FORMAT)
    String email,

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @StrongPassword(message = ValidationMessages.INVALID_PASSWORD_FORMAT)
    String password
) {
}
