package com.smartedu.demy.platform.iam.domain.model.commands;

/**
 * Command object used to request user authentication (sign-in).
 * Encapsulates the user's credentials for login.
 *
 * @param email    the email address associated with the user account.
 * @param password the plain text password provided for authentication.
 */
public record SignInUserAccountCommand(
        String email,
        String password
) {
}
