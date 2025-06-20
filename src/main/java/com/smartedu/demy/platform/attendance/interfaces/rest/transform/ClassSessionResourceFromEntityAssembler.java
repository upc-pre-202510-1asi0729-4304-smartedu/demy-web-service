package com.smartedu.demy.platform.attendance.interfaces.rest.transform;

import com.smartedu.demy.platform.attendance.domain.model.aggregates.ClassSession;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.AttendanceRecordResource;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.ClassSessionResource;

/**
 * Assembler to create a ClassSessionResource from a ClassSession entity.
 */
public class ClassSessionResourceFromEntityAssembler {
    /**
     * Converts a ClassSession entity to a ClassSessionResource
     *
     */
    public static ClassSessionResource toResourceFromEntity(ClassSession entity) {
        return new ClassSessionResource(
                entity.getCourseId().value().toString(),
                entity.getDate().toString(),
                entity.getAttendance().stream()
                        .map(record -> new AttendanceRecordResource(
                                record.studentId().value().toString(),
                                record.status().name()
                        )).toList()
        );
    }
}
