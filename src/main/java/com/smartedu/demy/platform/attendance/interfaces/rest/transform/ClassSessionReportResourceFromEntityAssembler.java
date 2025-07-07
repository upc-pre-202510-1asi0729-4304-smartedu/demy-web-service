package com.smartedu.demy.platform.attendance.interfaces.rest.transform;

import com.smartedu.demy.platform.attendance.domain.model.aggregates.ClassSession;
import com.smartedu.demy.platform.attendance.domain.model.entities.AttendanceRecord;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.AttendanceReportResource;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.ClassSessionReportResource;

import java.util.List;

/**
 * @name ClassSessionReportResourceFromEntityAssembler
 * @summary
 * Assembler class responsible for transforming AttendanceRecord domain entities
 * into a ClassSessionReportResource for REST API responses.
 *
 * <p>
 * This class helps decouple the domain model from REST representations
 * by converting attendance records into resources grouped by class session.
 * </p>
 *
 * @since 1.0
 */
public class ClassSessionReportResourceFromEntityAssembler {

    public static ClassSessionReportResource toResourceFromEntities(
            Long courseId,
            List<AttendanceRecord> records
    ) {
        var attendanceResources = records.stream()
                .map(record -> new AttendanceReportResource(
                        record.getDni().dni(),
                        record.getStudentName(),
                        record.getStatus().name(),
                        record.getClassSession().getDate()
                ))
                .toList();

        return new ClassSessionReportResource(courseId, attendanceResources);
    }
}
