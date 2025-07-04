package com.smartedu.demy.platform.iam.domain.model.commands;

/**
 * Command object used to request the update of an existing academy's information.
 *
 * @param id           the unique identifier of the academy to be updated.
 * @param academyName  the new name for the academy.
 * @param ruc          the new RUC (tax identification number) for the academy.
 */
public record UpdateAcademyCommand(
        Long id,
        String academyName,
        String ruc
) {
}
