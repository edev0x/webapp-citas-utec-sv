package com.utec.citasutec.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AppointmentLogsActions {
    APPOINTMENT_CREATED("Cita creada"),
    STATE_UPDATE("Actualización de estado"),
    REMINDER_SENT("Envio de recordatorio"),
    USER_ASSIGNED("Reasigna usuario"),
    PROFESSIONAL_ASSIGNED("Reasigna profesional");

    @Getter
    private final String message;
}
