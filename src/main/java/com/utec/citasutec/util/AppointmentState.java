package com.utec.citasutec.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum AppointmentState {
    PENDING("PENDIENTE"),
    CONFIRMED("CONFIRMADA"),
    WAITING("EN_ESPERA"),
    CANCELLED("CANCELADA"),
    ATTENDING("EN_ATENCION"),
    NO_ASSISTANCE("SIN_ASISTENCIA"),
    RESCHEDULED("REAGENDADA"),
    COMPLETED("COMPLETADA"),
    SUSPENDED("SUSPENDIDA");

    private final String translation;

    public boolean isValidState(String state) {
        if (state == null) {
            return false;
        }
        return Stream.of(AppointmentState.values())
            .map(AppointmentState::getTranslation)
            .anyMatch(state::equalsIgnoreCase);
    }
}
