package com.smartedu.demy.platform.enrollment.interfaces.rest.transform;

import com.smartedu.demy.platform.enrollment.domain.model.commands.CreateStudentCommand;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.CreateStudentResource;

public class CreateStudentCommandFromResourceAssembler {
    public static CreateStudentCommand toCommandFromResource(CreateStudentResource resource) {
        return new CreateStudentCommand(
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
