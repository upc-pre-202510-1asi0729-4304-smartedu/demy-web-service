package com.smartedu.demy.platform.enrollment.interfaces.rest.resources;


import java.time.LocalDate;

public record CreateStudentResource(String firstName, String lastName, String dni, String sex, LocalDate birthDate, String address, String phoneNumber) {
    public CreateStudentResource {

    }
}
