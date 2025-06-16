package com.smartedu.demy.platform.iam.interfaces.rest.resources;

import java.time.LocalDate;

public record UserAccountResource(
        Long id,
        String fullName,
        String email,
        String role,
        String status
) {}
