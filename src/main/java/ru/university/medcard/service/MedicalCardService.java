package ru.university.medcard.service;

import ru.university.medcard.exception.AppointmentBookingException;
import ru.university.medcard.model.Appointment;
import ru.university.medcard.model.CabinetWorkingHours;
import ru.university.medcard.model.Doctor;
import ru.university.medcard.model.Patient;
import ru.university.medcard.model.Prescription;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


public class MedicalCardService {

    private final List<Appointment> appointments = new ArrayList<>();
    private final CabinetWorkingHours workingHours;
    private final AtomicInteger idCounter = new AtomicInteger(100);

    public MedicalCardService(LocalTime openTime, LocalTime closeTime) {
        this.workingHours = new CabinetWorkingHours(openTime, closeTime);
    }

    public Appointment bookAppointment(Patient patient, Doctor doctor, LocalDateTime startTime)
            throws AppointmentBookingException {
        Objects.requireNonNull(patient, "Пациент не может быть null");
        Objects.requireNonNull(doctor, "Врач не может быть null");
        Objects.requireNonNull(startTime, "Время начала не может быть null");

        var duration = doctor.getSpecialty().getAppointmentDuration();
        var endTime = startTime.plus(duration);
        var cabinet = doctor.getSpecialty().getCabinet();

        if (!workingHours.includes(startTime.toLocalTime(), endTime.toLocalTime())) {
            throw new AppointmentBookingException(String.format(
                    "Время записи %s-%s выходит за пределы рабочих часов (%s-%s)",
                    startTime.toLocalTime(), endTime.toLocalTime(),
                    workingHours.openTime(), workingHours.closeTime()
            ));
        }

        for (var existing : appointments) {
            if (existing.overlapsWith(startTime, endTime)) {
                if (existing.getDoctor().equals(doctor)) {
                    throw new AppointmentBookingException(
                            "Врач " + doctor.getFullName() + " уже занят в это время (" +
                                    existing.getStartTime() + " - " + existing.getEndTime() + ")"
                    );
                }
                if (existing.getCabinet().equalsIgnoreCase(cabinet)) {
                    throw new AppointmentBookingException(
                            "Кабинет " + cabinet + " уже занят другим приёмом в это время (" +
                                    existing.getStartTime() + " - " + existing.getEndTime() + ")"
                    );
                }
            }
        }

        var appointment = new Appointment("A-" + idCounter.incrementAndGet(), patient, doctor, cabinet, startTime);
        appointments.add(appointment);
        return appointment;
    }

    public List<Appointment> getPatientHistory(Patient patient, LocalDate from, LocalDate to) {
        Objects.requireNonNull(patient, "Пациент не может быть null");
        Objects.requireNonNull(from, "Дата 'from' не может быть null");
        Objects.requireNonNull(to, "Дата 'to' не может быть null");

        var result = new ArrayList<Appointment>();
        for (var app : appointments) {
            if (app.getPatient().equals(patient)) {
                var appDate = app.getStartTime().toLocalDate();
                if (!appDate.isBefore(from) && !appDate.isAfter(to)) {
                    result.add(app);
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<Prescription> getActivePrescriptions(Patient patient, LocalDate date) {
        Objects.requireNonNull(patient, "Пациент не может быть null");
        Objects.requireNonNull(date, "Дата не может быть null");

        var active = new ArrayList<Prescription>();
        for (var app : appointments) {
            if (app.getPatient().equals(patient)) {
                for (var pr : app.getPrescriptions()) {
                    if (pr.isActiveOn(date)) {
                        active.add(pr);
                    }
                }
            }
        }
        return Collections.unmodifiableList(active);
    }

    public List<Appointment> getAllAppointments() {
        return Collections.unmodifiableList(new ArrayList<>(appointments));
    }
}