package ru.university.medcard.model;

import java.time.LocalDate;
import java.util.Objects;

public class MedicationPrescription implements Prescription {

    private final String medicineName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String dosage;

    public MedicationPrescription(String medicineName, LocalDate startDate, LocalDate endDate, String dosage) {
        this.medicineName = Objects.requireNonNull(medicineName, "Название лекарства не может быть null");
        this.startDate = Objects.requireNonNull(startDate, "Дата начала не может быть null");
        this.endDate = Objects.requireNonNull(endDate, "Дата окончания не может быть null");
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Дата окончания не может быть раньше даты начала");
        }
        this.dosage = Objects.requireNonNull(dosage, "Дозировка не может быть null");
    }

    @Override
    public String getTitle() {
        return medicineName;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String getDetails() {
        return "Лекарство: " + medicineName + ", дозировка: " + dosage +
                " (с " + startDate + " по " + endDate + ")";
    }

    @Override
    public String toString() {
        return getDetails();
    }
}