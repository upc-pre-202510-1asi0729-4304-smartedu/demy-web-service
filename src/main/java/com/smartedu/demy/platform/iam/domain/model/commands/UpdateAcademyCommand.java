package com.smartedu.demy.platform.iam.domain.model.commands;

public record UpdateAcademyCommand(
        Long id,
        String academyName,
        String ruc
) {
}
