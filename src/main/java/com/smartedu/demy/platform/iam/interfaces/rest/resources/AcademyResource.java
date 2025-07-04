package com.smartedu.demy.platform.iam.interfaces.rest.resources;

/**
 * Resource representation of an Academy entity exposed through the API.
 *
 * @param id           the unique identifier of the academy
 * @param academyName  the official name of the academy
 * @param ruc          the tax identification number (Registro Único de Contribuyentes) of the academy
 */
public record AcademyResource(
        String id,
        String academyName,
        String ruc
) {}
