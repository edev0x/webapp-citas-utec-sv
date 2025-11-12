package com.utec.citasutec.model.dto.response;

import com.utec.citasutec.model.entity.Appointment;

public record AppointmentResponseDto(
    Integer id,
    String appointmentDate,
    String appointmentStartTime,
    String appointmentEndTime,
    String state,
    Integer professionalId,
    String professionalName,
    Integer userId,
    String userFullName,
    String description
) {
    public static AppointmentResponseDto fromEntity(Appointment appointment) {
        return new AppointmentResponseDto(
            appointment.getId(),
            appointment.getAppointmentDate().toString(),
            appointment.getStartTime().toString(),
            appointment.getEndTime().toString(),
            appointment.getState(),
            appointment.getProfessional().getId(),
            appointment.getProfessional().getName(),
            appointment.getUser().getId(),
            appointment.getUser().getFullName(),
            appointment.getReason()
        );
    }
}
