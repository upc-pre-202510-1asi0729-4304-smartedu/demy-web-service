package com.smartedu.demy.platform.attendance.interfaces.rest.resources;


import java.time.LocalDate;

/**
 * @name AttendanceReportResource
 * @summary
 * REST resource representing a student's attendance report for a class session.
 * Includes the student's DNI, full name, attendance status, and the date of the session.
 *
 * <p>
 * This resource is typically returned in response to attendance queries,
 * allowing clients to view enriched attendance information.
 * </p>
 *
 * @param dni         the student's unique identifier (DNI)
 * @param studentName the full name of the student
 * @param status      the attendance status (e.g., PRESENT, ABSENT)
 * @param date        the date of the class session
 *
 * @since 1.0
 */
public record AttendanceReportResource(
        String dni,
        String studentName,
        String status,
        LocalDate date
) {
}
