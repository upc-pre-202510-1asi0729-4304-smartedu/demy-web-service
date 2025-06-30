package com.smartedu.demy.platform.iam.interfaces.rest.resources;

public record UserAccountResource(
        Long id,
        String fullName,
        String email,
        String role,
        String status
) {}
