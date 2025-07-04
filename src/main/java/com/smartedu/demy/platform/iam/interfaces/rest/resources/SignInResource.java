package com.smartedu.demy.platform.iam.interfaces.rest.resources;

/**
 * Resource object used for user sign-in authentication.
 *
 * @param email the user's email used for login
 * @param password the user's password used for authentication
 */
public record SignInResource(
        String email,
        String password
) {}

