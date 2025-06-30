package com.smartedu.demy.platform.attendance.interfaces.rest.transform;

import com.smartedu.demy.platform.attendance.domain.model.commands.CreateClassSessionCommand;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.CourseId;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.StudentId;
import com.smartedu.demy.platform.attendance.domain.model.valueobjects.AttendanceRecord;
import com.smartedu.demy.platform.attendance.domain.model.valueobjects.AttendanceStatus;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.CreateClassSessionResource;


import java.time.LocalDate;
import java.util.List;

/**
 * Assembler to create a CreateClassSessionCommand from a CreateClassSessionResource
 * @since 1.0
 */
public class CreateClassSessionCommandFromResourceAssembler {

    public static CreateClassSessionCommand toCommandFromResource(CreateClassSessionResource resource) {
        return new CreateClassSessionCommand(
                new CourseId(Long.parseLong(resource.courseId())),
                LocalDate.parse(resource.date()),
                resource.attendance().stream()
                        .map(a -> new AttendanceRecord(
                                new StudentId(Long.parseLong(a.studentId())),
                                AttendanceStatus.valueOf(a.status().toUpperCase())
                        ))
                        .toList()
        );
    }
}
