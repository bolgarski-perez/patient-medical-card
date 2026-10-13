package ru.university.medcard.model;

import java.time.LocalTime;
import java.util.Objects;

public record CabinetWorkingHours(LocalTime openTime, LocalTime closeTime) {

    public CabinetWorkingHours {
        Objects.requireNonNull(openTime, "Время открытия не может быть null");
        Objects.requireNonNull(closeTime, "Время закрытия не может быть null");
        if (!closeTime.isAfter(openTime)) {
            throw new IllegalArgumentException("Время закрытия должно быть позже открытия");
        }
    }

    public boolean includes(LocalTime start, LocalTime end) {
        return !start.isBefore(openTime) && !end.isAfter(closeTime);
    }
}