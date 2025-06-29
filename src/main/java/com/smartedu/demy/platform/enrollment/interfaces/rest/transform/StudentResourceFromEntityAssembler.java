package com.smartedu.demy.platform.enrollment.interfaces.rest.transform;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Enrollment;
import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Student;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.EnrollmentResource;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.StudentResource;

public class StudentResourceFromEntityAssembler {
    public static StudentResource toResourceFromEntity(Student entity) {
        return new StudentResource(
                entity.getId(),
                entity.getFullName().firstName(),
                entity.getFullName().lastName(),
                entity.getDni().dni(),
                entity.getSex().toString(),
                entity.getBirthDate(),
                entity.getAddress(),
                entity.getPhoneNumber().phoneNumber()
        );
    }
}
