package com.smartedu.demy.platform.iam.domain.services;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.commands.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * Service interface for handling user account command operations.
 * Encapsulates business logic related to the creation, authentication,
 * modification, and removal of user accounts.
 */
public interface UserAccountCommandService {

    /**
     * Handles the registration of a new admin user along with the creation of an associated academy.
     *
     * @param command the command containing admin user and academy details.
     * @return the created {@link UserAccount}.
     */
    UserAccount handle(SignUpAdminCommand command);

    /**
     * Handles the authentication (sign-in) of a user.
     *
     * @param command the command containing the user's login credentials.
     * @return an {@link ImmutablePair} containing the authenticated {@link UserAccount}
     *         and the generated JWT token.
     */
    ImmutablePair<UserAccount, String> handle(SignInUserAccountCommand command);

    /**
     * Handles the creation of a new teacher user account.
     *
     * @param command the command containing teacher account information.
     * @return the created {@link UserAccount}.
     */
    UserAccount handle(CreateTeacherCommand command);

    /**
     * Handles the update of an existing user account’s profile and role.
     *
     * @param command the command containing updated account details.
     * @return the updated {@link UserAccount}.
     */
    UserAccount handle(UpdateUserAccountCommand command);

    /**
     * Handles the deletion of a user account.
     *
     * @param command the command identifying the account to delete.
     */
    void handle(DeleteUserAccountCommand command);

    /**
     * Handles the reset of a user’s password.
     *
     * @param command the command containing the user’s email and new password.
     */
    void handle(ResetPasswordCommand command);
}