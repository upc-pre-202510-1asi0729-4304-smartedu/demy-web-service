package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.commands.UpdateUserAccountCommand;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.UpdateUserAccountResource;

public class UpdateUserAccountCommandFromResourceAssembler {
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
