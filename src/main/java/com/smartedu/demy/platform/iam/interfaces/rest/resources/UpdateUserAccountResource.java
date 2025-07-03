package com.smartedu.demy.platform.iam.interfaces.rest.resources;

public record  UpdateUserAccountResource(
        String firstName,
        String lastName,
        String email,
        String role
) {
}
