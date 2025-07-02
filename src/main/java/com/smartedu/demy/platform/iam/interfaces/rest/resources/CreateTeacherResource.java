package com.smartedu.demy.platform.iam.interfaces.rest.resources;

public record CreateTeacherResource(
        String firstName,
        String lastName,
        String email,
        String password
) {
}