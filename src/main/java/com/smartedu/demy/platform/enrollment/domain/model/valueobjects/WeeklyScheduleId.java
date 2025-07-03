package com.smartedu.demy.platform.enrollment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record WeeklyScheduleId(Long weeklyScheduleId) {
        public WeeklyScheduleId {
        if (weeklyScheduleId == null || weeklyScheduleId < 1) {
            throw new IllegalArgumentException("Weekly schedule ID cant be null or must be greater than 0");
        }
    }
}
