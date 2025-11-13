package com.utec.citasutec.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utec.citasutec.util.validators.ValidationMessages;
import com.utec.citasutec.util.validators.beans.ValidAppointmentState;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AppointmentRequestDto(
    @Nullable
    Integer id,
    @NotNull(message = ValidationMessages.USER_ID_REQUIRED)
    Integer userId,
    @NotNull(message = ValidationMessages.PROFESSIONAL_ID_REQUIRED)
    Integer professionalId,
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "UTC")
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @NotNull(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    String appointmentDate,
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING, timezone = "UTC")
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @NotNull(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    String appointmentStartTime,
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING, timezone = "UTC")
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @NotNull(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    String appointmentEndTime,
    @NotNull(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @ValidAppointmentState(message = ValidationMessages.INVALID_APPOINTMENT_STATE)
    String state,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    String reason
) {
}
