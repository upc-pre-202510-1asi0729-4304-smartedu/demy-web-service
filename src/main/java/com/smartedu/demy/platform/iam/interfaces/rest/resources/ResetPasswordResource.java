package com.smartedu.demy.platform.iam.interfaces.rest.resources;

public record ResetPasswordResource(
        String email,
        String newPassword
) {}
