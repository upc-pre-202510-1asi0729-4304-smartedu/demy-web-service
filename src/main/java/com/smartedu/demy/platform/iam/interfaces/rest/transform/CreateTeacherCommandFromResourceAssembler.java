package com.smartedu.demy.platform.iam.interfaces.rest.transform;

import com.smartedu.demy.platform.iam.domain.model.commands.CreateTeacherCommand;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.CreateTeacherResource;

/**
 * Assembler class to convert {@link CreateTeacherResource} DTOs
 * into {@link CreateTeacherCommand} domain commands.
 */
public class CreateTeacherCommandFromResourceAssembler {

    /**
     * Converts a {@link CreateTeacherResource} into a {@link CreateTeacherCommand}.
     *
     * @param resource the resource DTO containing teacher creation data
     * @return a new {@link CreateTeacherCommand} populated with data from the resource
     */
    public static CreateTeacherCommand toCommandFromResource(CreateTeacherResource resource) {
        return new CreateTeacherCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.password()
        );
    }
}
