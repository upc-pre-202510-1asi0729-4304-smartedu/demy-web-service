package com.smartedu.demy.platform.enrollment.domain.services;

import com.smartedu.demy.platform.enrollment.domain.model.aggregates.Enrollment;
import com.smartedu.demy.platform.enrollment.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface EnrollmentQueryService {
    List<Enrollment> handle(GetAllEnrollmentsByStudentIdQuery query);
    List<Enrollment> handle(GetAllEnrollmentsQuery query);
    Optional<Enrollment> handle(GetEnrollmentByIdQuery query);
    List<Enrollment> handle(GetAllEnrollmentsByStudentDniQuery query);
}
