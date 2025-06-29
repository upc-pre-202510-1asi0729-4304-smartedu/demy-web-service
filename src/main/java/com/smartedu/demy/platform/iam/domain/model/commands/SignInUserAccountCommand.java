package com.smartedu.demy.platform.iam.domain.model.commands;

public record SignInUserAccountCommand(
        String email,
        String password
) {
}
