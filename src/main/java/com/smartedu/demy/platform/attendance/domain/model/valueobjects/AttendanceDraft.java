package com.smartedu.demy.platform.attendance.domain.model.valueobjects;

import com.smartedu.demy.platform.shared.domain.model.valueobjects.Dni;

/**
 * Value Object representing a plain data input for attendance.
 *
 * <p>
 * Used when creating a new ClassSession to carry student attendance details
 * from external input (e.g., API request body). Unlike the embedded
 * AttendanceRecord entity, this class serves as a simple, flat data structure
 * for collecting input before mapping it into the domain model.
 * </p>
 *
 * @param dni    the student's unique identifier (DNI), must not be null
 * @param status the attendance status, must not be null
 * @throws IllegalArgumentException if any parameter is null
 * @since 1.0
 */
public record AttendanceDraft(
        Dni dni,
        AttendanceStatus status
) {
    public AttendanceDraft {
        if (dni == null) {
            throw new IllegalArgumentException("StudentId is required");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status is required");
        }
    }
}
