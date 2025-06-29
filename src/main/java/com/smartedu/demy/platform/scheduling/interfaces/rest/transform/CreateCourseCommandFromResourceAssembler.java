package com.smartedu.demy.platform.scheduling.interfaces.rest.transform;

import com.smartedu.demy.platform.scheduling.domain.model.commands.CreateCourseCommand;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.CreateCourseResource;

public class CreateCourseCommandFromResourceAssembler {
    /**
     * Converts a CreateCourseResource to a CreateCourseCommand
     * @param resource the CreateCourseResource
     * @return the CreateCourseCommand
     */
    public static CreateCourseCommand toCommandFromResource(CreateCourseResource resource) {
        return new CreateCourseCommand(
                resource.name(),
                resource.code(),
                resource.description()
        );
    }
}