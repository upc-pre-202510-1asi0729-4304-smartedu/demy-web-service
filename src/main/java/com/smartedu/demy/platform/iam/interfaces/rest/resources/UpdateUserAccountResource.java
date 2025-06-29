package com.smartedu.demy.platform.iam.interfaces.rest.resources;

public record  UpdateUserAccountResource(
        Long id,
        String firstName,
        String lastName,
        String email,
        String role
) {
}
