package com.smartedu.demy.platform.iam.application.internal.commandservices;

import com.smartedu.demy.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.smartedu.demy.platform.iam.application.internal.outboundservices.tokens.TokenService;
import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.commands.*;
import com.smartedu.demy.platform.iam.domain.model.aggregates.Academy;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.AccountStatus;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Email;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.FullName;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.iam.domain.services.UserAccountCommandService;
import com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories.AcademyRepository;
import com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserAccountCommandService} responsible for handling
 * commands related to user account creation, authentication, updates, deletions, and password resets.
 */
@Service
public class UserAccountCommandServiceImpl implements UserAccountCommandService {

    private final UserAccountRepository userAccountRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final AcademyRepository academyRepository;

    /**
     * Constructs the command service with required dependencies.
     *
     * @param userAccountRepository the repository for managing user accounts.
     * @param hashingService the service for password hashing and validation.
     * @param tokenService the service for generating JWT tokens.
     * @param academyRepository the repository for managing academy entities.
     */
    public UserAccountCommandServiceImpl(
            UserAccountRepository userAccountRepository,
            HashingService hashingService,
            TokenService tokenService,
            AcademyRepository academyRepository
    ) {
        this.userAccountRepository = userAccountRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.academyRepository = academyRepository;
    }

    /**
     * Handles the sign-up of a new admin user and creates an associated academy.
     *
     * @param command the command containing admin sign-up details.
     * @return the newly created admin user account.
     * @throws RuntimeException if the email is already registered or the input is invalid.
     */
    @Override
    public UserAccount handle(SignUpAdminCommand command) {
        userAccountRepository.findByEmail(new Email(command.email()))
                .ifPresent(u -> { throw new RuntimeException("Email already registered"); });

        if (command.email() == null || command.email().trim().isEmpty()) {
            throw new RuntimeException("Invalid input");
        }
        var admin = new UserAccount(
                new FullName(command.firstName(), command.lastName()),
                new Email(command.email()),
                hashingService.encode(command.password()),
                Roles.ADMIN,
                AccountStatus.ACTIVE
        );

        var savedAdmin = userAccountRepository.save(admin);

        var academy = new Academy(
                savedAdmin.getUserId(),
                command.academyName(),
                command.ruc()
        );

        academyRepository.save(academy);
        return savedAdmin;
    }

    /**
     * Handles user sign-in by validating credentials and generating a JWT token.
     *
     * @param command the command containing email and password for login.
     * @return a pair containing the authenticated user and the generated JWT token.
     * @throws RuntimeException if the user is not found or the password is invalid.
     */
    @Override
    public ImmutablePair<UserAccount, String> handle(SignInUserAccountCommand command) {
        var optionalUser = userAccountRepository.findByEmail(new Email(command.email()));

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        var user = optionalUser.get();

        if (!hashingService.matches(command.password(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }
        String jwtToken = tokenService.generateToken(user.getEmail().value());

        return ImmutablePair.of(user, jwtToken);
    }

    /**
     * Handles the creation of a new teacher user account.
     *
     * @param command the command containing teacher details.
     * @return the newly created teacher user account.
     * @throws RuntimeException if the email is already registered or the input is invalid.
     */
    @Override
    public UserAccount handle(CreateTeacherCommand command) {

        if (command.email() == null || command.email().trim().isEmpty()) {
            throw new RuntimeException("Invalid input");
        }

        userAccountRepository.findByEmail(new Email(command.email()))
                .ifPresent(u -> { throw new RuntimeException("Email already registered"); });
        var teacher = new UserAccount(

                new FullName(command.firstName(), command.lastName()),
                new Email(command.email()),
                hashingService.encode(command.password()),
                Roles.TEACHER,
                AccountStatus.ACTIVE
        );
        return userAccountRepository.save(teacher);
    }

    /**
     * Handles updating an existing user account's profile and role.
     *
     * @param command the command containing updated user information.
     * @return the updated user account.
     * @throws RuntimeException if the user is not found.
     */
    @Override
    public UserAccount handle(UpdateUserAccountCommand command) {
        var user = userAccountRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updateProfile(new FullName(command.firstName(), command.lastName()), new Email(command.email()));
        user.updateRole(command.role());

        return userAccountRepository.save(user);
    }

    /**
     * Handles deletion of a user account by ID.
     *
     * @param command the command specifying the user account to delete.
     */
    @Override
    public void handle(DeleteUserAccountCommand command) {
        userAccountRepository.deleteById(command.id());
    }

    /**
     * Handles resetting the password for a user account.
     *
     * @param command the command containing email and new password.
     * @throws RuntimeException if the user is not found.
     */
    @Override
    public void handle(ResetPasswordCommand command) {
        UserAccount user = userAccountRepository.findByEmail(new Email(command.email()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updatePassword(hashingService.encode(command.newPassword()));
        userAccountRepository.save(user);
    }
}
