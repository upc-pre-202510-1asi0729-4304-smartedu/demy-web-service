package com.smartedu.demy.platform.enrollment.interfaces.rest.transform;

import com.smartedu.demy.platform.enrollment.domain.model.commands.UpdateStudentCommand;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.UpdateStudentResource;


public class UpdateStudentCommandFromResourceAssembler {
    public static UpdateStudentCommand toCommandFromResource(Long studentId, UpdateStudentResource resource) {
        return new UpdateStudentCommand(
                studentId,
                resource.firstName(),
                resource.lastName(),
                resource.dni(),
                resource.sex(),
                resource.birthDate(),
                resource.address(),
                resource.phoneNumber()
        );
    }
}
