package com.utec.citasutec.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utec.citasutec.util.validators.ValidationMessages;
import com.utec.citasutec.util.validators.beans.ValidAppointmentState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDto(
    @NotNull(message = ValidationMessages.USER_ID_REQUIRED)
    Integer userId,
    @NotNull(message = ValidationMessages.PROFESSIONAL_ID_REQUIRED)
    Integer professionalId,
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "UTC")
    @NotNull(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    LocalDate appointmentDate,
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "UTC")
    @NotNull(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    LocalTime appointmentStartTime,
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "UTC")
    @NotNull(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    LocalTime appointmentEndTime,
    @NotNull(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    @ValidAppointmentState(message = ValidationMessages.INVALID_APPOINTMENT_STATE)
    String state,
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_ERROR_GENERIC)
    String reason
) {
}
