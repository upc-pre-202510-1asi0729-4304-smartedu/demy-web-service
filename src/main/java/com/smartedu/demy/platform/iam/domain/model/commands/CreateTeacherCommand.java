package com.smartedu.demy.platform.iam.domain.model.commands;

/**
 * Command object used to request the creation of a new teacher user account.
 * Encapsulates all necessary input data required by the application layer.
 *
 * @param firstName the teacher's first name.
 * @param lastName  the teacher's last name.
 * @param email     the teacher's email address, used for identification and login.
 * @param password  the plain text password to be hashed and stored.
 */
public record CreateTeacherCommand(String firstName, String lastName, String email, String password) {
}
