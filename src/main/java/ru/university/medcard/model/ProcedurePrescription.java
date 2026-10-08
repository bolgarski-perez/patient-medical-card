package ru.university.medcard.model;

import java.time.LocalDate;
import java.util.Objects;

public class ProcedurePrescription implements Prescription {

    private final String procedureName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int sessionCount;

    public ProcedurePrescription(String procedureName, LocalDate startDate, LocalDate endDate, int sessionCount) {
        this.procedureName = Objects.requireNonNull(procedureName, "Название процедуры не может быть null");
        this.startDate = Objects.requireNonNull(startDate, "Дата начала не может быть null");
        this.endDate = Objects.requireNonNull(endDate, "Дата окончания не может быть null");
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Дата окончания не может быть раньше даты начала");
        }
        if (sessionCount <= 0) {
            throw new IllegalArgumentException("Количество сеансов должно быть больше 0");
        }
        this.sessionCount = sessionCount;
    }

    @Override
    public String getTitle() {
        return procedureName;
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
        return "Процедура: " + procedureName + ", сеансов: " + sessionCount +
                " (с " + startDate + " по " + endDate + ")";
    }

    @Override
    public String toString() {
        return getDetails();
    }
}