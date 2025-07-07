package com.smartedu.demy.platform.enrollment.domain.model.commands;

import java.time.LocalDate;

public record UpdateStudentCommand(Long studentId, String firstName, String lastName, String dni, String sex, LocalDate birthDate, String address, String phoneNumber ) {
    public UpdateStudentCommand {
        if (studentId == null || studentId < 1) {
            throw new IllegalArgumentException("studentId must be greater than 0");
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName cannot be null or blank");
        }
        if (dni == null || dni.isBlank() || dni.length() != 8) {
            throw new IllegalArgumentException("dni cannot be null or blank");
        }
        if (phoneNumber == null || phoneNumber.isBlank() || phoneNumber.length() != 9) {
            throw new IllegalArgumentException("phoneNumber cannot be null or blank");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("address cannot be null or blank");
        }
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("birthDate cannot be null or after today");
        }
    }
}
