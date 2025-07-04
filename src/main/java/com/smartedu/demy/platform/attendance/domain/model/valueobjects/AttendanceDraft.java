package com.smartedu.demy.platform.attendance.domain.model.valueobjects;

import com.smartedu.demy.platform.shared.domain.model.valueobjects.Dni;

public record AttendanceDraft(
        Dni dni,
        AttendanceStatus status
) {
    public AttendanceDraft {
        if (dni == null) {
            throw new IllegalArgumentException("StudentId is required");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status is required");
        }
    }
}
