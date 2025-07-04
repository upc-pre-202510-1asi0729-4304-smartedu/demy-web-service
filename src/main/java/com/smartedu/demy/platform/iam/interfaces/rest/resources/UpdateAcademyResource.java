package com.smartedu.demy.platform.iam.interfaces.rest.resources;


/**
 * Resource object used to update an existing academy's information.
 *
 * @param id the unique identifier of the academy to be updated
 * @param academyName the new name of the academy
 * @param ruc the updated RUC (Registro Único de Contribuyentes) of the academy
 */
public record UpdateAcademyResource(
        Long id,
        String academyName,
        String ruc
) {
}
