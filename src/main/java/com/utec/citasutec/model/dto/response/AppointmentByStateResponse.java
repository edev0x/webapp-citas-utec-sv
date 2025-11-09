package com.utec.citasutec.model.dto.response;

public record AppointmentByStateResponse(
    int totalAppointments,
    String state
) {
}
