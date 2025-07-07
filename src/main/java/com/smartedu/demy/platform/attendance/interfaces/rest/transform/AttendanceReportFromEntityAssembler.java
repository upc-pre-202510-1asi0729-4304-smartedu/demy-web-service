package com.smartedu.demy.platform.attendance.interfaces.rest.transform;

import com.smartedu.demy.platform.attendance.domain.model.entities.AttendanceRecord;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.AttendanceReportResource;

/**
 * @name AttendanceReportFromEntityAssembler
 * @summary
 * Assembler class responsible for transforming AttendanceRecord domain entities
 * into AttendanceReportResource REST resources.
 *
 * <p>
 * Used to decouple the internal domain model from the REST API representations
 * by mapping entities to resources.
 * </p>
 *
 * @since 1.0
 */
public class AttendanceReportFromEntityAssembler {
    public static AttendanceReportResource toResourceFromEntity(AttendanceRecord entity) {
        return new AttendanceReportResource(
                entity.getDni().dni(),
                entity.getStudentName(),
                entity.getStatus().name(),
                entity.getClassSession().getDate()
        );
    }
}
