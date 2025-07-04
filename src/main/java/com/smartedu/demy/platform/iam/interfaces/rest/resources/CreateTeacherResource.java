package com.smartedu.demy.platform.iam.interfaces.rest.resources;

/**
 * Resource representation for creating a teacher account.
 *
 * @param firstName the teacher's first name
 * @param lastName  the teacher's last name
 * @param email     the teacher's email address
 * @param password  the password for the teacher account
 */
public record CreateTeacherResource(
        String firstName,
        String lastName,
        String email,
        String password
) {
}