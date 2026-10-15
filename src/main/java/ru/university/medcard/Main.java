package ru.university.medcard;

import ru.university.medcard.exception.AppointmentBookingException;
import ru.university.medcard.model.Diagnosis;
import ru.university.medcard.model.Doctor;
import ru.university.medcard.model.MedicationPrescription;
import ru.university.medcard.model.Patient;
import ru.university.medcard.model.ProcedurePrescription;
import ru.university.medcard.model.Specialty;
import ru.university.medcard.service.MedicalCardService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   ДЕМОНСТРАЦИЯ: МЕДИЦИНСКАЯ КАРТА ПАЦИЕНТА ");
        System.out.println("====================================================================\n");

        var service = new MedicalCardService(LocalTime.of(8, 0), LocalTime.of(20, 0));

        var doc1 = new Doctor("D1", "Иванов И.И.", Specialty.THERAPIST);
        var doc2 = new Doctor("D2", "Смирнова А.В.", Specialty.THERAPIST);
        var doc3 = new Doctor("D3", "Соколов П.К.", Specialty.CARDIOLOGIST);
        var doc4 = new Doctor("D4", "Кузнецова Е.М.", Specialty.NEUROLOGIST);
        var doc5 = new Doctor("D5", "Морозов Д.С.", Specialty.SURGEON);

        var p1 = new Patient("111-222-333 01", "Петров Алексей Петрович", LocalDate.of(1985, 3, 14));
        var p2 = new Patient("222-333-444 02", "Сидорова Анна Сергеевна", LocalDate.of(1992, 7, 22));
        var p3 = new Patient("333-444-555 03", "Васильев Олег Игоревич", LocalDate.of(1978, 11, 5));
        var p4 = new Patient("444-555-666 04", "Николаева Ольга Павловна", LocalDate.of(2001, 1, 19));
        var p5 = new Patient("555-666-777 05", "Федоров Михаил Юрьевич", LocalDate.of(1964, 9, 30));

        var dFlu = new Diagnosis("J10", "Грипп");
        var dHyper = new Diagnosis("I10", "Гипертония");
        var dMigraine = new Diagnosis("G43", "Мигрень");
        var dBronch = new Diagnosis("J20", "Острый бронхит");
        var dSprain = new Diagnosis("S93", "Растяжение связок");
        var dGastritis = new Diagnosis("K29", "Гастрит");

        var prAmoxi = new MedicationPrescription("Амоксициллин", LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 7), "500 мг 3 р/д");
        var prInhal = new ProcedurePrescription("Ингаляции", LocalDate.of(2026, 4, 2), LocalDate.of(2026, 4, 8), 7);
        var prLisin = new MedicationPrescription("Лизиноприл", LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 30), "10 мг 1 р/д утром");
        var prCardio = new MedicationPrescription("Кардиомагнил", LocalDate.of(2026, 4, 5), LocalDate.of(2026, 4, 20), "75 мг 1 р/д");
        var prSumatr = new MedicationPrescription("Суматриптан", LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 13), "50 мг при приступе");
        var prMassage = new ProcedurePrescription("Лечебный массаж", LocalDate.of(2026, 4, 3), LocalDate.of(2026, 4, 17), 10);

        System.out.println("Созданы базовые справочники: 5 врачей, 5 пациентов, 6 диагнозов, 6 назначений.");

        System.out.println("\n--- 1. Успешная запись на приём к врачам ---");
        try {
            var a1 = service.bookAppointment(p1, doc1, LocalDateTime.of(2026, 4, 1, 9, 0));
            a1.setDiagnosis(dFlu);
            a1.addPrescription(prAmoxi);
            a1.addPrescription(prInhal);

            var a2 = service.bookAppointment(p2, doc1, LocalDateTime.of(2026, 4, 1, 9, 30));
            a2.setDiagnosis(dBronch);

            var a3 = service.bookAppointment(p3, doc3, LocalDateTime.of(2026, 4, 1, 10, 0));
            a3.setDiagnosis(dHyper);
            a3.addPrescription(prLisin);
            a3.addPrescription(prCardio);

            var a4 = service.bookAppointment(p4, doc4, LocalDateTime.of(2026, 4, 1, 11, 0));
            a4.setDiagnosis(dMigraine);
            a4.addPrescription(prSumatr);

            var a5 = service.bookAppointment(p5, doc5, LocalDateTime.of(2026, 4, 1, 12, 0));
            a5.setDiagnosis(dSprain);
            a5.addPrescription(prMassage);

            var a6 = service.bookAppointment(p1, doc1, LocalDateTime.of(2026, 4, 8, 14, 0));
            a6.setDiagnosis(dGastritis);

            var a7 = service.bookAppointment(p2, doc3, LocalDateTime.of(2026, 4, 8, 15, 0));
            var a8 = service.bookAppointment(p3, doc4, LocalDateTime.of(2026, 4, 9, 10, 0));
            var a9 = service.bookAppointment(p4, doc5, LocalDateTime.of(2026, 4, 9, 11, 0));
            var a10 = service.bookAppointment(p5, doc2, LocalDateTime.of(2026, 4, 10, 16, 0));

            System.out.println("Успешно создано приёмов: " + service.getAllAppointments().size());
            System.out.println("ИТОГО создано объектов: 5 + 5 + 6 + 6 + 10 = 32 объекта (> 30).");
            System.out.println("Пример созданного приёма: " + a1);
        } catch (AppointmentBookingException e) {
            System.err.println("Ошибка при записи: " + e.getMessage());
        }

        System.out.println("\n--- 2. Обработка коллизий (AppointmentBookingException) ---");

        try {
            System.out.println("Попытка записать пациента к Иванову на 09:15 (врач занят 09:00 - 09:30):");
            service.bookAppointment(p5, doc1, LocalDateTime.of(2026, 4, 1, 9, 15));
        } catch (AppointmentBookingException e) {
            System.out.println(" -> Исключение перехвачено: " + e.getMessage());
        }

        try {
            System.out.println("Попытка записи на 07:30 (кабинет открывается в 08:00):");
            service.bookAppointment(p2, doc1, LocalDateTime.of(2026, 4, 2, 7, 30));
        } catch (AppointmentBookingException e) {
            System.out.println(" -> Исключение перехвачено: " + e.getMessage());
        }

        System.out.println("\n--- 3. История приёмов пациента " + p1.getFullName() + " с 01.04.2026 по 10.04.2026 ---");
        var history = service.getPatientHistory(p1, LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 10));
        for (var app : history) {
            System.out.println(" * " + app.getStartTime() + " | Врач: " + app.getDoctor().getFullName()
                    + " | Диагноз: " + (app.getDiagnosis() != null ? app.getDiagnosis().title() : "нет"));
        }

        // 8. Действующие назначения на дату
        var checkDate = LocalDate.of(2026, 4, 5);
        System.out.println("\n--- 4. Действующие назначения пациента " + p1.getFullName() + " на дату " + checkDate + " ---");
        var active = service.getActivePrescriptions(p1, checkDate);
        for (var pr : active) {
            System.out.println(" * " + pr.getDetails());
        }

        System.out.println("\n====================================================================");
        System.out.println("   ДЕМОНСТРАЦИЯ ЗАВЕРШЕНА");
        System.out.println("====================================================================");
    }
}