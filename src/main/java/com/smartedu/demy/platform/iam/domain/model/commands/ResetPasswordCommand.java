package com.smartedu.demy.platform.iam.domain.model.commands;

/**
 * Command object used to request a password reset for a user account.
 * Encapsulates the user's email and the new password to be set.
 *
 * @param email       the email address associated with the user account.
 * @param newPassword the new plain text password to be hashed and stored.
 */
public record ResetPasswordCommand(
        String email,
        String newPassword
) {
}
