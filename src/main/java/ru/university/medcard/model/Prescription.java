package ru.university.medcard.model;

import java.time.LocalDate;

public interface Prescription {

    String getTitle();
    LocalDate getStartDate();

    LocalDate getEndDate();

    String getDetails();

    default boolean isActiveOn(LocalDate date) {
        if (date == null) {
            return false;
        }
        return !date.isBefore(getStartDate()) && !date.isAfter(getEndDate());
    }
}