package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record UpdateWeeklyScheduleNameCommand(
        Long weeklyScheduleId,
        String name
) {
    public UpdateWeeklyScheduleNameCommand {
        if (weeklyScheduleId == null || weeklyScheduleId <= 0) {
            throw new IllegalArgumentException("weeklyScheduleId cannot be null or less than 1");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
    }
}