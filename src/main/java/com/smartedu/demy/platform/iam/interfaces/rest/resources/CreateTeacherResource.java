package com.smartedu.demy.platform.iam.interfaces.rest.resources;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.resources.Email;
import com.smartedu.demy.platform.iam.domain.model.resources.FullName;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.AccountStatus;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.shared.domain.model.services.PasswordHasher;

public record CreateTeacherResource(
        String firstName,
        String lastName,
        String email,
        String password
) {
    public UserAccount toEntity() {
        return new UserAccount(
                new FullName(firstName, lastName),
                new Email(email),
                PasswordHasher.hash(password),
                Roles.TEACHER,
                AccountStatus.ACTIVE
        );
    }
}