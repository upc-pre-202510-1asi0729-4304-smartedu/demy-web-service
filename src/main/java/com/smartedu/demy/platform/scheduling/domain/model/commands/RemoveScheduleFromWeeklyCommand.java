package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record RemoveScheduleFromWeeklyCommand(
        Long weeklyScheduleId,
        Long scheduleId
) {
    public RemoveScheduleFromWeeklyCommand {
        if (weeklyScheduleId == null || weeklyScheduleId <= 0) {
            throw new IllegalArgumentException("WeeklyScheduleId cannot be null or less than 1");
        }
        if(scheduleId == null || scheduleId <= 0) {
            throw new IllegalArgumentException("ScheduleId cannot be null or less than 1");
        }

    }
}