package com.smartedu.demy.platform.attendance.interfaces.rest.resources;

/**
 * Resource record for save a list of attendance records.
 * @summary
 * This record represent the attendance of a student in a class session.
 * It contains the student ID and status
 * @since 1.0
 */
public record AttendanceRecordResource(
        String studentId,
        String status
) {
    /**
     * Valides the resource
     * @throws IllegalArgumentException If studentId or status is null or empty
     */
    public AttendanceRecordResource{
        if(studentId == null || studentId.isEmpty())
            throw new IllegalArgumentException("Student ID cannot be null");
        if(status == null || status.isBlank())
            throw new IllegalArgumentException("Status cannot be null");
    }
}
