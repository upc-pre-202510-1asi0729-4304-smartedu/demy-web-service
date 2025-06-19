package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record UpdateWeeklyScheduleNameCommand(
        Long weeklyScheduleId,
        String name
) {}