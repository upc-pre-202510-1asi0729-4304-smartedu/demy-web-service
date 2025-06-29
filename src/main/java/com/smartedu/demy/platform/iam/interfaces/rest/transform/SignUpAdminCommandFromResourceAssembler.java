package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.commands.SignUpAdminCommand;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.SignUpAdminResource;

public class SignUpAdminCommandFromResourceAssembler {
    public static SignUpAdminCommand toCommandFromResource(SignUpAdminResource resource) {
        return new SignUpAdminCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.password()
        );
    }
}
