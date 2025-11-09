package com.utec.citasutec.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReminderState {
    PENDING("PENDIENTE"),
    SENT("ENVIADO"),
    DELIVERED("ENTREGADO"),
    FAILED("FALLIDO"),
    CANCELLED("CANCELADO");

    private final String translation;
}
