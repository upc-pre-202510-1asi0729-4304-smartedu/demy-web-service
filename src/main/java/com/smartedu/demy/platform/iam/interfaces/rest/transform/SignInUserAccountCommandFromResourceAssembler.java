package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.commands.SignInUserAccountCommand;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.SignInResource;

public class SignInUserAccountCommandFromResourceAssembler {
    public static SignInUserAccountCommand toCommandFromResource(SignInResource resource) {
        return new SignInUserAccountCommand(
                resource.email(),
                resource.password()
        );
    }
}
