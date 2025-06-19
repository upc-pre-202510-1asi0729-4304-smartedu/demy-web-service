package com.smartedu.demy.platform.enrollment.interfaces.rest.resources;

import java.time.LocalDate;

public record StudentResource(Long studentId, String firstName, String lastName, String dni, String sex, LocalDate birthDate, String address, String phoneNumber) {
    public StudentResource {
        if (studentId == null || studentId < 1) {
            throw new IllegalArgumentException("Student id is mandatory");
        }
    }
}
