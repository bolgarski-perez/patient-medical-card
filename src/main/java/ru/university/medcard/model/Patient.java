package ru.university.medcard.model;

import java.time.LocalDate;
import java.util.Objects;

public class Patient {

    private final String policyNumber;
    private final String fullName;
    private final LocalDate birthDate;

    public Patient(String policyNumber, String fullName, LocalDate birthDate) {
        this.policyNumber = Objects.requireNonNull(policyNumber, "Номер полиса не может быть null").trim();
        if (this.policyNumber.isEmpty()) {
            throw new IllegalArgumentException("Номер полиса не может быть пустым");
        }
        this.fullName = Objects.requireNonNull(fullName, "ФИО не может быть null").trim();
        this.birthDate = Objects.requireNonNull(birthDate, "Дата рождения не может быть null");
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var patient = (Patient) o;
        return policyNumber.equals(patient.policyNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(policyNumber);
    }

    @Override
    public String toString() {
        return fullName + " (Полис: " + policyNumber + ")";
    }
}