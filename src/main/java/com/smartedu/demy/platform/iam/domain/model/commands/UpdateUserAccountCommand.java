package com.smartedu.demy.platform.iam.domain.model.commands;

import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;

/**
 * Command object used to request the update of an existing user account's profile and role.
 *
 * @param id        the unique identifier of the user account to be updated.
 * @param firstName the updated first name of the user.
 * @param lastName  the updated last name of the user.
 * @param email     the updated email address of the user.
 * @param role      the new role to assign to the user.
 */
public record UpdateUserAccountCommand(
        Long id,
        String firstName,
        String lastName,
        String email,
        Roles role
) {
}
