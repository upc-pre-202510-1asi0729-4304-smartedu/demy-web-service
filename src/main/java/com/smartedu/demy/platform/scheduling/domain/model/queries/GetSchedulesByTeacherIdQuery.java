package com.smartedu.demy.platform.scheduling.domain.model.queries;

import com.smartedu.demy.platform.shared.domain.model.valueobjects.UserId;

public record GetSchedulesByTeacherIdQuery (UserId teacherId) {
    public GetSchedulesByTeacherIdQuery{
        if (teacherId == null || teacherId.value() <= 0) {
            throw new IllegalArgumentException("TeacherId cannot be null or less than 1");
        }
    }
}
