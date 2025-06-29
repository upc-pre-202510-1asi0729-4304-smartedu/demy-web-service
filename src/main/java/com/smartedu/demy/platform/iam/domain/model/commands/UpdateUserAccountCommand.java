package com.smartedu.demy.platform.iam.domain.model.commands;

import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;

public record UpdateUserAccountCommand(
        Long id,
        String firstName,
        String lastName,
        String email,
        Roles role
) {
}
