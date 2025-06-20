package com.smartedu.demy.platform.enrollment.interfaces.rest.transform;

import com.smartedu.demy.platform.enrollment.domain.model.commands.UpdateEnrollmentCommand;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.UpdateEnrollmentResource;

import java.util.Currency;

public class UpdateEnrollmentCommandFromResourceAssembler {
    public static UpdateEnrollmentCommand toCommandFromResource(Long enrollmentId, UpdateEnrollmentResource resource) {
        return new UpdateEnrollmentCommand(
                enrollmentId,
                resource.amount(),
                resource.currency(),
                resource.enrollmentStatus(),
                resource.paymentStatus()
        )   ;
    }
}
