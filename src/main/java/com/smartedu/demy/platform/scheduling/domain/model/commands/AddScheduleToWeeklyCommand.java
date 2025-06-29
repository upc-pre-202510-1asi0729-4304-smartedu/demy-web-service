package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record AddScheduleToWeeklyCommand(
        Long weeklyScheduleId,
        String startTime,
        String endTime,
        String dayOfWeek,
        Long courseId,
        Long classroomId
) {}