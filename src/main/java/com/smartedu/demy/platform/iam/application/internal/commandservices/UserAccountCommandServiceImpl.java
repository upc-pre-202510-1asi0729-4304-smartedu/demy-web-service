package com.smartedu.demy.platform.iam.application.internal.commandservices;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.commands.*;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.AccountStatus;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Email;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.FullName;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.iam.domain.services.UserAccountCommandService;
import com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import com.smartedu.demy.platform.shared.domain.model.services.PasswordHasher;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

@Service
public class UserAccountCommandServiceImpl implements UserAccountCommandService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountCommandServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserAccount handle(SignUpAdminCommand command) {
        userAccountRepository.findByEmail(new Email(command.email()))
                .ifPresent(u -> { throw new RuntimeException("Email already registered"); });

        var admin = new UserAccount(
                new FullName(command.firstName(), command.lastName()),
                new Email(command.email()),
                PasswordHasher.hash(command.password()),
                Roles.ADMIN,
                AccountStatus.ACTIVE
        );

        return userAccountRepository.save(admin);
    }

    @Override
    public ImmutablePair<UserAccount, String> handle(SignInUserAccountCommand command) {
        var user = userAccountRepository.findByEmail(new Email(command.email()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!PasswordHasher.matches(command.password(), user.getPasswordHash()))
            throw new RuntimeException("Invalid credentials");

        // Validar rol si es necesario
        // if (command.role() != null && user.getRole() != command.role()) { ... }

        String jwtToken = "TODO_generateJWTTokenFor(user)";

        return ImmutablePair.of(user, jwtToken);
    }

    @Override
    public UserAccount handle(CreateTeacherCommand command) {
        var teacher = new UserAccount(
                new FullName(command.firstName(), command.lastName()),
                new Email(command.email()),
                PasswordHasher.hash(command.password()),
                Roles.TEACHER,
                AccountStatus.ACTIVE
        );
        return userAccountRepository.save(teacher);
    }

    @Override
    public UserAccount handle(UpdateUserAccountCommand command) {
        var user = userAccountRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updateProfile(new FullName(command.firstName(), command.lastName()), new Email(command.email()));
        user.updateRole(command.role());

        return userAccountRepository.save(user);
    }

    @Override
    public void handle(DeleteUserAccountCommand command) {
        userAccountRepository.deleteById(command.id());
    }

    @Override
    public void handle(ResetPasswordCommand command) {
        UserAccount user = userAccountRepository.findByEmail(new Email(command.email()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updatePassword(PasswordHasher.hash(command.newPassword()));
        userAccountRepository.save(user);
    }
}
