package com.utec.citasutec.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    public static List<Map<String, String>> getStates() {
        return Arrays.stream(AppointmentState.values())
            .map(state -> Map.of("value", state.getTranslation(), "translation", state.getTranslation().replace("_", " ")))
            .collect(Collectors.toList());
    }
}
