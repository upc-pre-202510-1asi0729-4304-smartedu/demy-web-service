package com.smartedu.demy.platform.scheduling.domain.model.commands;

public record UpdateScheduleCommand (
        Long scheduleId,
        Long classroomId,
        String startTime,
        String endTime,
        String dayOfWeek
) {
    public UpdateScheduleCommand {
        if(scheduleId == null || scheduleId <= 0) {
            throw new IllegalArgumentException("Schedule ID must be a positive number.");
        }
        if(classroomId == null || classroomId<= 0) {
            throw new IllegalArgumentException("Classroom Id must not be null or blank.");
        }
        if(startTime == null || startTime.isBlank()) {
            throw new IllegalArgumentException("StartTime  must not be null or blank.");
        }
        if(endTime == null || endTime.isBlank()) {
            throw new IllegalArgumentException("EndTime  must not be null or blank.");
        }
        if(dayOfWeek == null || dayOfWeek.isBlank()) {
            throw new IllegalArgumentException("DayOfWeek  must not be null or blank.");
        }
    }
}