package com.smartedu.demy.platform.scheduling.interfaces.rest.resources;

public record ScheduleResource(
        Long id,
        String startTime,
        String endTime,
        String dayOfWeek,
        Long courseId,
        Long classroomId
) {
}