package com.smartedu.demy.platform.attendance.domain.services;

import com.smartedu.demy.platform.attendance.domain.model.entities.AttendanceRecord;
import com.smartedu.demy.platform.attendance.domain.model.queries.GetAttendanceRecordsByDniCourseAndDateRangeQuery;

import java.util.List;

/**
 * @name ClassSessionQueryService
 * @summary
 * This interface defines the service for querying attendance records
 * based on student DNI, course, and a date range.
 */
public interface ClassSessionQueryService {
    /**
     * Handles the query to retrieve attendance records for a student
     * given their DNI, course, and date range.
     *
     * @param query the query containing DNI, course ID, and date range criteria
     * @return the list of matching attendance records
     *
     * @see GetAttendanceRecordsByDniCourseAndDateRangeQuery
     */
    List<AttendanceRecord> handle(GetAttendanceRecordsByDniCourseAndDateRangeQuery query);
}
