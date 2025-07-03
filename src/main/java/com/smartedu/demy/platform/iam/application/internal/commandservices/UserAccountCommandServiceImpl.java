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

@Service
public class UserAccountCommandServiceImpl implements UserAccountCommandService {

    private final UserAccountRepository userAccountRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final AcademyRepository academyRepository;

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

        user.updatePassword(hashingService.encode(command.newPassword()));
        userAccountRepository.save(user);
    }
}
