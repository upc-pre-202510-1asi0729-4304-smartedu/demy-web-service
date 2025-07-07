package com.smartedu.demy.platform.attendance.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.List;

/**
 * @name ClassSessionReportResource
 * @summary
 * REST resource representing a report of a single class session.
 * Includes the course ID and the list of attendance records for that session.
 *
 * <p>
 * This resource is typically used to return the full attendance
 * details of a class session, including individual student attendance
 * information enriched with student names and statuses.
 * </p>
 *
 * @param courseId    the unique identifier of the course to which the session belongs
 * @param attendance  the list of attendance report entries for students in this session
 *
 * @since 1.0
 */
public record ClassSessionReportResource(Long courseId, List<AttendanceReportResource> attendance) {
}
