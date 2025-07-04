package com.smartedu.demy.platform.iam.interfaces.rest.resources;

/**
 * Resource object for updating user account information.
 *
 * @param firstName the user's first name
 * @param lastName the user's last name
 * @param email the user's email address
 * @param role the user's role (e.g., ADMIN, TEACHER, etc.)
 */
public record  UpdateUserAccountResource(
        String firstName,
        String lastName,
        String email,
        String role
) {
}
