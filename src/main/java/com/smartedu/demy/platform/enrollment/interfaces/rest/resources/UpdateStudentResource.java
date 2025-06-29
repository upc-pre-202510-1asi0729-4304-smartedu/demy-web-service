package com.smartedu.demy.platform.enrollment.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateStudentResource(String firstName, String lastName, String dni, String sex, LocalDate birthDate, String address, String phoneNumber ) {
    public UpdateStudentResource {
        if (firstName == null || firstName.isBlank()) {

        }
    }
}
