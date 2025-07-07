package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.commands.UpdateUserAccountCommand;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.UpdateUserAccountResource;

/**
 * Assembler class to convert {@link UpdateUserAccountResource} DTOs
 * into {@link UpdateUserAccountCommand} domain commands.
 */
public class UpdateUserAccountCommandFromResourceAssembler {

    /**
     * Converts a {@link UpdateUserAccountResource} and an ID into an {@link UpdateUserAccountCommand}.
     *
     * @param id the user account ID to update
     * @param resource the resource DTO containing updated user details
     * @return a new {@link UpdateUserAccountCommand} populated with data from the resource and ID
     */
    public static UpdateUserAccountCommand toCommandFromResource(Long id,UpdateUserAccountResource resource) {
        return new UpdateUserAccountCommand(
                id,
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                Roles.valueOf(resource.role())
        );
    }
}
