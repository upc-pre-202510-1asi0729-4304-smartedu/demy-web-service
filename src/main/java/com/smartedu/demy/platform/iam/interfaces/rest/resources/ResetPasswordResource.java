package com.smartedu.demy.platform.iam.interfaces.rest.resources;

/**
 * Resource object used to carry the necessary information for resetting a user's password.
 *
 * @param email the email of the user requesting the password reset
 * @param newPassword the new password to be set for the user
 */
public record ResetPasswordResource(
        String email,
        String newPassword
) {}
