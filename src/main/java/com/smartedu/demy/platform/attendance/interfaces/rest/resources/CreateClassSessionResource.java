package com.smartedu.demy.platform.attendance.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.List;

/**
 * Resource  record for creating a class session.
 * @summary
 * This record represents the resource for creating a class session.
 * @since 1.0
 */
public record CreateClassSessionResource(
        String courseId,
        String date,
        List<AttendanceRecordResource> attendance) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException if courseId, date  or attendance is null
     */
    public CreateClassSessionResource{
        if(courseId == null ||courseId.isEmpty() )
            throw new IllegalArgumentException("Course ID cannot be null");
        if(date == null|| date.isBlank())
            throw new IllegalArgumentException("Date cannot be null or in the past");
        if(attendance == null || attendance.isEmpty())
            throw new IllegalArgumentException("Attendance cannot be null or empty");
    }
}
