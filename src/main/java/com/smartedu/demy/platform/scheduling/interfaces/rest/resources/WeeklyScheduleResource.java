package com.smartedu.demy.platform.scheduling.interfaces.rest.resources;

import java.util.List;

public record WeeklyScheduleResource(
        Long id,
        String name,
        List<ScheduleResource> schedules
) {
}