package com.smartedu.demy.platform.iam.interfaces.rest.resources;

public record UpdateAcademyResource(
        Long id,
        String academyName,
        String ruc
) {
}
