package ru.university.medcard.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Appointment {

    private final String id;
    private final Patient patient;
    private final Doctor doctor;
    private final String cabinet;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private Diagnosis diagnosis;
    private final List<Prescription> prescriptions = new ArrayList<>();

    public Appointment(String id, Patient patient, Doctor doctor, String cabinet, LocalDateTime startTime) {
        this.id = Objects.requireNonNull(id, "ID не может быть null");
        this.patient = Objects.requireNonNull(patient, "Пациент не может быть null");
        this.doctor = Objects.requireNonNull(doctor, "Врач не может быть null");
        this.cabinet = Objects.requireNonNull(cabinet, "Кабинет не может быть null");
        this.startTime = Objects.requireNonNull(startTime, "Время начала не может быть null");
        this.endTime = startTime.plus(doctor.getSpecialty().getAppointmentDuration());
    }

    public String getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getCabinet() {
        return cabinet;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void addPrescription(Prescription prescription) {
        if (prescription != null) {
            prescriptions.add(prescription);
        }
    }

    public List<Prescription> getPrescriptions() {
        return Collections.unmodifiableList(new ArrayList<>(prescriptions));
    }

    public boolean overlapsWith(LocalDateTime otherStart, LocalDateTime otherEnd) {
        return startTime.isBefore(otherEnd) && otherStart.isBefore(endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (Appointment) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Приём " + id + " [" + startTime + " - " + endTime + "] Врач: " + doctor.getFullName()
                + ", Пациент: " + patient.getFullName() + ", Кабинет: " + cabinet;
    }
}