package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.commands.CreateTeacherCommand;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.CreateTeacherResource;

public class CreateTeacherCommandFromResourceAssembler {
    public static CreateTeacherCommand toCommandFromResource(CreateTeacherResource resource) {
        return new CreateTeacherCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.password()
        );
    }
}
