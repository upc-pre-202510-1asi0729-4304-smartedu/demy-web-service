
package com.smartedu.demy.platform.attendance.domain.model.commands;


import com.smartedu.demy.platform.shared.domain.model.valueobjects.CourseId;
import com.smartedu.demy.platform.attendance.domain.model.valueobjects.AttendanceRecord;
import java.util.List;
import java.time.LocalDate;
/**
 * CreateClassSessionCommand
 * @summary
 * CreateClassSessionCommand is a record class that represents the command to create a favorite news source.
 */
public record CreateClassSessionCommand(
        CourseId courseId,
        LocalDate date,
        List<AttendanceRecord> attendance
) {
    /**
     * Validates the command.
     * @throws IllegalArgumentException If courseId or date is null
     *
     */
    public CreateClassSessionCommand {
        if(courseId == null )
            throw new IllegalArgumentException("Course ID is required");
        if(date == null || date.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Date is required or  cannot be in the past");
        if(attendance == null || attendance.isEmpty())
            throw new IllegalArgumentException("Attendance is required or  cannot be empty");
    }
}
