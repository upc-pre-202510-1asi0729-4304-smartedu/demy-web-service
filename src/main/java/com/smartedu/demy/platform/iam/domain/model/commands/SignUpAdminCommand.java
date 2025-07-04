package com.smartedu.demy.platform.iam.domain.model.commands;

/**
 * Command object used to request the registration of a new admin user
 * along with the creation of their associated academy.
 *
 * @param firstName    the admin's first name.
 * @param lastName     the admin's last name.
 * @param email        the admin's email address, used for login.
 * @param password     the plain text password to be hashed and stored.
 * @param academyName  the name of the academy to be created.
 * @param ruc          the RUC (tax identification number) of the academy.
 */
public record SignUpAdminCommand(
        String firstName,
        String lastName,
        String email,
        String password,
        String academyName,
        String ruc
) {
}
