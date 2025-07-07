package com.smartedu.demy.platform.iam.interfaces.rest.resources;

/**
 * Resource representing a user account for API responses.
 *
 * @param id the unique identifier of the user
 * @param fullName the full name of the user
 * @param email the user's email address
 * @param role the role assigned to the user (e.g., ADMIN, TEACHER)
 * @param status the current status of the user account (e.g., ACTIVE, INACTIVE)
 */
public record UserAccountResource(
        Long id,
        String fullName,
        String email,
        String role,
        String status
) {}
