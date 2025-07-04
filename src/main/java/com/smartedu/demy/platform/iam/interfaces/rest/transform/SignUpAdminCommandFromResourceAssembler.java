package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.commands.SignUpAdminCommand;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.SignUpAdminResource;

/**
 * Assembler class to convert {@link SignUpAdminResource} DTOs
 * into {@link SignUpAdminCommand} domain commands.
 */
public class SignUpAdminCommandFromResourceAssembler {
    /**
     * Converts a {@link SignUpAdminResource} into a {@link SignUpAdminCommand}.
     *
     * @param resource the resource DTO containing admin sign-up details
     * @return a new {@link SignUpAdminCommand} populated with data from the resource
     */
    public static SignUpAdminCommand toCommandFromResource(SignUpAdminResource resource) {
        return new SignUpAdminCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.password(),
                resource.academyName(),
                resource.ruc()
        );
    }
}
