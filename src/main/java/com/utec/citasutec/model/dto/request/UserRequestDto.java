package com.utec.citasutec.model.dto.request;

import com.utec.citasutec.model.dto.response.RoleDto;
import com.utec.citasutec.util.validators.ValidationConstants;
import com.utec.citasutec.util.validators.ValidationMessages;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
    Integer id,
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
    @Nullable
    String password,
    Boolean isActive,
    @NotNull(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    RoleDto role
) {
}
