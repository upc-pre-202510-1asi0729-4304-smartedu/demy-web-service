package com.smartedu.demy.platform.enrollment.interfaces.rest.transform;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Enrollment;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.EnrollmentResource;


public class EnrollmentResourceFromEntityAssembler {
    public static EnrollmentResource toResourceFromEntity(Enrollment entity) {
        return new EnrollmentResource(
                entity.getId(),
                entity.getStudentId().studentId(),
                entity.getAcademicPeriodId().periodId(),
                entity.getWeeklyScheduleId().weeklyScheduleId(),
                entity.getAmount().amount(),
                entity.getAmount().currency(),
                entity.getEnrollmentStatus().toString(),
                entity.getPaymentStatus().toString()
        );
    }
}
