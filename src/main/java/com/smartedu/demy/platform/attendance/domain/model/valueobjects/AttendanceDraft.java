package com.smartedu.demy.platform.attendance.domain.model.valueobjects;

import com.smartedu.demy.platform.shared.domain.model.valueobjects.StudentId;

public record AttendanceDraft(
        StudentId studentId,
        AttendanceStatus status
) {
    public AttendanceDraft {
        if (studentId == null) {
            throw new IllegalArgumentException("StudentId is required");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status is required");
        }
    }
}
