package com.smartedu.demy.platform.enrollment.interfaces.rest.transform;

import com.smartedu.demy.platform.enrollment.domain.model.commands.CreateEnrollmentCommand;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.CreateEnrollmentResource;

public class CreateEnrollmentCommandFromResourceAssembler {
    public static CreateEnrollmentCommand toCommandFromResource(CreateEnrollmentResource resource) {
        return new CreateEnrollmentCommand(
                resource.studentId(),
                resource.periodId(),
                resource.weeklyScheduleName(),
                resource.amount(),
                resource.currency(),
                resource.enrollmentStatus(),
                resource.paymentStatus()
        );
    }
}
