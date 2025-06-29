package com.smartedu.demy.platform.scheduling.interfaces.rest.transform;

import com.smartedu.demy.platform.scheduling.domain.model.commands.CreateClassroomCommand;
import com.smartedu.demy.platform.scheduling.interfaces.rest.resources.CreateClassroomResource;

public class CreateClassroomCommandFromResourceAssembler {
    /**
     * Converts a CreateClassroomResource to a CreateClassroomCommand
     * @param resource the CreateClassroomResource
     * @return the CreateClassroomCommand
     */
    public static CreateClassroomCommand toCommandFromResource(CreateClassroomResource resource) {
        return new CreateClassroomCommand(
                resource.code(),
                resource.capacity(),
                resource.campus()
        );
    }
}