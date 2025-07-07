package com.smartedu.demy.platform.iam.interfaces.rest.resources;

/**
 * Resource object used to register a new admin user along with their associated academy.
 *
 * @param firstName the first name of the admin
 * @param lastName the last name of the admin
 * @param email the email address for the admin account
 * @param password the password for the admin account
 * @param academyName the name of the academy associated with the admin
 * @param ruc the RUC (Registro Único de Contribuyentes) of the academy
 */
public record SignUpAdminResource(
        String firstName,
        String lastName,
        String email,
        String password,
        String academyName,
        String ruc
) {
}
