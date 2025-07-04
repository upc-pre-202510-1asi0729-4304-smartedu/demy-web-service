package com.smartedu.demy.platform.iam.domain.model.commands;

/**
 * Command object used to request the deletion of a user account.
 * Encapsulates the unique identifier of the user to be deleted.
 *
 * @param id the unique identifier of the user account to delete.
 */
public record DeleteUserAccountCommand(Long id) {
}
