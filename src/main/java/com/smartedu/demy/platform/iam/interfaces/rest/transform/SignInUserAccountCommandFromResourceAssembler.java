package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.commands.SignInUserAccountCommand;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.SignInResource;

/**
 * Assembler class to convert {@link SignInResource} DTOs
 * into {@link SignInUserAccountCommand} domain commands.
 */
public class SignInUserAccountCommandFromResourceAssembler {

    /**
     * Converts a {@link SignInResource} into a {@link SignInUserAccountCommand}.
     *
     * @param resource the resource DTO containing sign-in credentials
     * @return a new {@link SignInUserAccountCommand} populated with data from the resource
     */
    public static SignInUserAccountCommand toCommandFromResource(SignInResource resource) {
        return new SignInUserAccountCommand(
                resource.email(),
                resource.password()
        );
    }
}
