package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record RemoveScheduleFromWeeklyCommand(
        Long weeklyScheduleId,
        Long scheduleId
) {}