package ru.university.medcard.model;

import java.time.Duration;

public enum Specialty {
    THERAPIST("Кабинет 101", Duration.ofMinutes(30)),
    CARDIOLOGIST("Кабинет 205", Duration.ofMinutes(45)),
    NEUROLOGIST("Кабинет 302", Duration.ofMinutes(40)),
    SURGEON("Кабинет 110", Duration.ofMinutes(20));

    private final String cabinet;
    private final Duration appointmentDuration;

    Specialty(String cabinet, Duration appointmentDuration) {
        this.cabinet = cabinet;
        this.appointmentDuration = appointmentDuration;
    }

    public String getCabinet() {
        return cabinet;
    }

    public Duration getAppointmentDuration() {
        return appointmentDuration;
    }
}