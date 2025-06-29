package com.smartedu.demy.platform.iam.domain.model.commands;

public record ResetPasswordCommand(
        String email,
        String newPassword
) {
}
