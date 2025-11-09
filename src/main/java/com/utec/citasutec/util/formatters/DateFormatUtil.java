package com.utec.citasutec.util.formatters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatUtil {
    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("dd 'de' MMMM 'del' yyyy", Locale.forLanguageTag("es-SV"));

    private static final DateTimeFormatter TIME_FORMATTER =
        DateTimeFormatter.ofPattern("hh:mm a", Locale.forLanguageTag("es-SV"));

    public static String formatDate(LocalDateTime date) {
        if (date == null) return "";
        return date.format(DATE_FORMATTER);
    }

    public static String formatTime(LocalTime time) {
        if (time == null) return "";
        return time.format(TIME_FORMATTER);
    }

    public static String formatLocalDate(LocalDate date) {
        if (date == null) return "";
        return date.format(DATE_FORMATTER);
    }
}
