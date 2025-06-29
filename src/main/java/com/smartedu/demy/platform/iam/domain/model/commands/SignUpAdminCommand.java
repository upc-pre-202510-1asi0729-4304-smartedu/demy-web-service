package com.smartedu.demy.platform.iam.domain.model.commands;

public record SignUpAdminCommand(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
