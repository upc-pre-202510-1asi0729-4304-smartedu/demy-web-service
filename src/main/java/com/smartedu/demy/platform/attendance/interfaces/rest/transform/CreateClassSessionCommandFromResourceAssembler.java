package com.smartedu.demy.platform.attendance.interfaces.rest.transform;

import com.smartedu.demy.platform.attendance.domain.model.commands.CreateClassSessionCommand;
import com.smartedu.demy.platform.attendance.domain.model.valueobjects.AttendanceDraft;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.CourseId;
import com.smartedu.demy.platform.shared.domain.model.valueobjects.Dni;
import com.smartedu.demy.platform.attendance.domain.model.valueobjects.AttendanceStatus;
import com.smartedu.demy.platform.attendance.interfaces.rest.resources.CreateClassSessionResource;


import java.time.LocalDate;

/**
 * Assembler to create a CreateClassSessionCommand from a CreateClassSessionResource
 * @since 1.0
 */
public class CreateClassSessionCommandFromResourceAssembler {

    public static CreateClassSessionCommand toCommandFromResource(CreateClassSessionResource resource) {
        return new CreateClassSessionCommand(
                new CourseId(resource.courseId()),
                resource.date(),
                resource.attendance().stream()
                        .map(a -> new AttendanceDraft(
                                new Dni(a.dni()),
                                AttendanceStatus.valueOf(a.status().toUpperCase())
                        ))
                        .toList()
        );
    }
}
