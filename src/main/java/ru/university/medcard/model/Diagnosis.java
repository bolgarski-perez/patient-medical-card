package ru.university.medcard.model;

import java.util.Objects;

public record Diagnosis(String code, String title) {

    public Diagnosis {
        Objects.requireNonNull(code, "Код диагноза не может быть null");
        Objects.requireNonNull(title, "Название диагноза не может быть null");
    }
}