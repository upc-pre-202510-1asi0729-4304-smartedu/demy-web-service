package com.smartedu.demy.platform.scheduling.interfaces.rest.resources;

public record AddScheduleToWeeklyResource(
        String startTime,
        String endTime,
        String dayOfWeek,
        Long courseId,
        Long classroomId,
        String teacherFirstName,
        String teacherLastName
) {
}