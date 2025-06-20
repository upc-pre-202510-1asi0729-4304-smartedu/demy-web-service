package com.smartedu.demy.platform.enrollment.domain.model.queries;

public record GetAllEnrollmentsByStudentDniQuery(String dni) {
    public GetAllEnrollmentsByStudentDniQuery {
        if (dni == null || dni.length() != 8 || dni.isBlank()) {
            throw new IllegalArgumentException("dni is null or empty or invalid");
        }
    }
}
