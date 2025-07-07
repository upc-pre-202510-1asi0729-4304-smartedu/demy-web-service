package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.commands.ResetPasswordCommand;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.ResetPasswordResource;


/**
 * Assembler class to convert {@link ResetPasswordResource} DTOs
 * into {@link ResetPasswordCommand} domain commands.
 */
public class ResetPasswordCommandFromResourceAssembler {

    /**
     * Converts a {@link ResetPasswordResource} into a {@link ResetPasswordCommand}.
     *
     * @param resource the resource DTO containing reset password data
     * @return a new {@link ResetPasswordCommand} populated with data from the resource
     */
    public static ResetPasswordCommand toCommandFromResource(ResetPasswordResource resource) {
        return new ResetPasswordCommand(
                resource.email(),
                resource.newPassword()
        );
    }
}
