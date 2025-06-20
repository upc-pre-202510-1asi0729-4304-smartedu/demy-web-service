package com.smartedu.demy.platform.enrollment.domain.model.queries;

public record GetAllEnrollmentsByStudentIdQuery(Long studentId) {
    public GetAllEnrollmentsByStudentIdQuery {
        if (studentId == null || studentId < 1) {
            throw new IllegalArgumentException("studentId must be greater than or equal to 1");
        }
    }
}
