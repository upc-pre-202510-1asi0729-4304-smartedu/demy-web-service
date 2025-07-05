package com.smartedu.demy.platform.enrollment.interfaces.rest.resources;

import java.time.LocalDate;

public record StudentResource(Long id, String firstName, String lastName, String dni, String sex, LocalDate birthDate, String address, String phoneNumber) {
    public StudentResource {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("Student id is mandatory");
        }
    }
}
