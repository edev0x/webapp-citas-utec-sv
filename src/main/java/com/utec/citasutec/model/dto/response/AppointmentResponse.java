package com.utec.citasutec.model.dto.response;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import com.utec.citasutec.model.entity.Appointment;

public record AppointmentResponse(
        Integer id,
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        LocalTime endTime,
        String state,
        UserResponseDto user,
        ProfessionalResponseDto professional,
        Instant createdAt) {

    public static AppointmentResponse fromEntity(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getAppointmentDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getState(),
                UserResponseDto.fromEntity(appointment.getUser()),
                ProfessionalResponseDto.fromEntity(appointment.getProfessional()),
                appointment.getCreationDate()
        );
    }
}
