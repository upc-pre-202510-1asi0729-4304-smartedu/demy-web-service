package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record DeleteWeeklyScheduleCommand (Long weeklyScheduleId){
    public DeleteWeeklyScheduleCommand {
        if (weeklyScheduleId == null || weeklyScheduleId <= 0) {
            throw new IllegalArgumentException("WeeklyScheduleId cannot be null or less than 1");
        }
    }
}
