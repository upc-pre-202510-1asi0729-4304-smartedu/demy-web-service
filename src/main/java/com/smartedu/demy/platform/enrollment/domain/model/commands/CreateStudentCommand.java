package com.smartedu.demy.platform.enrollment.domain.model.commands;

import java.time.LocalDate;

public record CreateStudentCommand(String firstName, String lastName, String dni, String sex, LocalDate birthDate, String address, String phoneNumber) {
    public CreateStudentCommand {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName cannot be null or blank");
        }
        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException("dni cannot be null or blank");
        }
        if (dni.length() != 8) {
            throw new IllegalArgumentException("dni must be exactly 8 characters long");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("phoneNumber cannot be null or blank");
        }
        if (phoneNumber.length() != 9) {
            throw new IllegalArgumentException("phoneNumber must be exactly 9 characters long");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("address cannot be null or blank");
        }
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("birthDate cannot be null or after today");
        }
    }
}
