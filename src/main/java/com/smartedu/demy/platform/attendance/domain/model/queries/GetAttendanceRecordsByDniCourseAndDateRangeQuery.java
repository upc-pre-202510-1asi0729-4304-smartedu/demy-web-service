package com.smartedu.demy.platform.attendance.domain.model.queries;

import com.smartedu.demy.platform.shared.domain.model.valueobjects.Dni;

import java.time.LocalDate;

public record GetAttendanceRecordsByDniCourseAndDateRangeQuery(
        Dni dni,
        Long courseId,
        LocalDate startDate,
        LocalDate endDate
) {
    public GetAttendanceRecordsByDniCourseAndDateRangeQuery {
        if (dni == null || dni.dni() == null || dni.dni().isBlank()) {
            throw new IllegalArgumentException("Dni cannot be null or empty.");
        }
        if (courseId == null) {
            throw new IllegalArgumentException("CourseId cannot be null.");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("StartDate and EndDate cannot be null.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("StartDate cannot be after EndDate.");
        }
    }
}
