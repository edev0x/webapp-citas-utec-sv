package com.utec.citasutec.model.dto.response;

public record AppointmentByMonthResponse(
    int year,
    int month,
    int numberOfAppointments
) {
}
