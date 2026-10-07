package ru.university.medcard.model;

import java.util.Objects;

public class Doctor {

    private final String id;
    private final String fullName;
    private final Specialty specialty;

    public Doctor(String id, String fullName, Specialty specialty) {
        this.id = Objects.requireNonNull(id, "ID врача не может быть null");
        this.fullName = Objects.requireNonNull(fullName, "ФИО врача не может быть null");
        this.specialty = Objects.requireNonNull(specialty, "Специальность не может быть null");
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var doctor = (Doctor) o;
        return id.equals(doctor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return fullName + " (" + specialty + ", " + specialty.getCabinet() + ")";
    }
}