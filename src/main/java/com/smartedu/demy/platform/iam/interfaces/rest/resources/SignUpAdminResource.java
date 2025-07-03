package com.smartedu.demy.platform.iam.interfaces.rest.resources;

public record SignUpAdminResource(
        String firstName,
        String lastName,
        String email,
        String password,
        String academyName,
        String ruc
) {
}
